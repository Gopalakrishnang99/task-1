package com.gopal.task.one.service.impl;

import com.gopal.task.one.dto.RoleFeatureDto;
import com.gopal.task.one.dto.UserDetailsDto;
import com.gopal.task.one.dto.UserRolesDataDto;
import com.gopal.task.one.exception.ApiException;
import com.gopal.task.one.exception.UserNotFoundException;
import com.gopal.task.one.mapper.UserMapper;
import com.gopal.task.one.model.Role;
import com.gopal.task.one.model.RolePermission;
import com.gopal.task.one.repository.RoleRepository;
import com.gopal.task.one.repository.UserDetailsRepository;
import com.gopal.task.one.repository.UserRoleDataRepository;
import com.gopal.task.one.repository.UserRoleRepository;
import com.gopal.task.one.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;

/**
 * Service layer to handle user and role logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userRepo;

    private final UserRoleRepository userRoleRepo;

    private final RoleRepository roleRepo;

    private final UserRoleDataRepository userRoleDataRepo;

    private final WebClient webClient;

    private final UserMapper userMapper;

    /**
     * Takes a user-id and returns the corresponding user details, fetching
     * it from the database
     * @param userId The user-id
     * @return The user details
     */
    @Override
    @Transactional
    public Mono<UserDetailsDto> getUserDetails(Long userId) {
        log.info("Getting the user details for {}",userId);
        return userRepo.findById(userId).switchIfEmpty(
                Mono.error(() -> new UserNotFoundException("User with ID " + userId + " is not found"))
        ).map(userMapper::userEntityToDto);
    }

    /**
     * Takes a user-id and returns the roles assigned for that particular
     * user
     * @param userId The user-id
     * @return The set of roles assigned to the user
     */
    @Override
    @Transactional
    public Flux<String> getRolesOfUser(Long userId) {
        log.info("Getting the roles of user {}",userId);
        return userRoleRepo.findAllByUserId(userId)
                .flatMap(role ->
                        roleRepo.findById(role.getRoleId()))
                .map(Role::getRoleName);
    }

    /**
     * Takes a user-id and returns the roles assigned to the user and the feature
     * permissions the assigned roles grant to the user.
     * @param userId The user-id
     * @return The roles and feature set available to the user
     */
    @Override
    @Transactional
    public Mono<UserRolesDataDto> getRoleDetailsOfUser(Long userId) {

        UserRolesDataDto rolesDataDto = new UserRolesDataDto();

        // Get the roles of the user by calling an API of the same service
        Mono<Set<String>> rolesSet = webClient
                .get()
                .uri("/user/{userId}/roles", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new ApiException("Error getting roles data")))
                .bodyToMono(new ParameterizedTypeReference<Set<String>>() {
                })
                .subscribeOn(Schedulers.boundedElastic());

        // Get the user details from the db
        Mono<UserDetailsDto> userDetails = getUserDetails(userId)
                .subscribeOn(Schedulers.boundedElastic());

        // Get the role permission mapping from the db
        Mono<List<RolePermission>> roleFeaturesSet = userRoleRepo
                .findAllByUserId(userId)
                .flatMap(userRole -> {
                    return userRoleDataRepo.getRoleFeatures(userRole.getRoleId());
                })
                .collectList().defaultIfEmpty(List.of())
                .subscribeOn(Schedulers.boundedElastic());

        // Zip the three Mono together and generate the dto object
        return Mono.zip(rolesSet, userDetails, roleFeaturesSet).map(tuple3 -> {
            rolesDataDto.setRoles(tuple3.getT1()); // roles data
            rolesDataDto.setUser(tuple3.getT2()); // user data
            Map<Long, RoleFeatureDto> featureMap = new HashMap<>();
            tuple3.getT3().forEach(rolePermission -> { // feature data
                if (!featureMap.containsKey(rolePermission.getFeatureId())) {
                    featureMap.put(rolePermission.getFeatureId(), new RoleFeatureDto(
                            rolePermission.getFeatureId(),
                            rolePermission.getFeature().getFeatureName())
                    );
                }
                featureMap.get(rolePermission.getFeatureId())
                        .getPermission()
                        .add(rolePermission
                                .getPermission()
                                .getPermissionName()
                        );
            });
            rolesDataDto.setFeatures(new HashSet<>(featureMap.values()));
            return rolesDataDto;
        });
    }
}
