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

    @Override
    @Transactional
    public Mono<UserDetailsDto> getUserDetails(Long userId) {
        return userRepo.findById(userId).switchIfEmpty(
                Mono.error(() -> new UserNotFoundException("User with ID " + userId + " is not found"))
        ).map(userMapper::userEntityToDto);
    }

    @Override
    @Transactional
    public Flux<String> getRolesOfUser(Long userId) {
        return userRoleRepo.findAllByUserId(userId)
                .flatMap(role ->
                        roleRepo.findById(role.getRoleId()))
                .map(Role::getRoleName);
    }

    @Override
    @Transactional
    public Mono<UserRolesDataDto> getRoleDetailsOfUser(Long userId) {

        UserRolesDataDto rolesDataDto = new UserRolesDataDto();

        Mono<Set<String>> rolesSet = webClient
                .get()
                .uri("/user/{userId}/roles", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new ApiException("Error getting roles data")))
                .bodyToMono(new ParameterizedTypeReference<Set<String>>() {
                })
                .subscribeOn(Schedulers.boundedElastic());

        Mono<UserDetailsDto> userDetails = getUserDetails(userId)
                .subscribeOn(Schedulers.boundedElastic());

        Mono<List<RolePermission>> roleFeaturesSet = userRoleRepo
                .findAllByUserId(userId)
                .flatMap(userRole -> {
                    return userRoleDataRepo.getRoleFeatures(userRole.getRoleId());
                })
                .collectList().defaultIfEmpty(List.of())
                .subscribeOn(Schedulers.boundedElastic());

        return Mono.zip(rolesSet, userDetails, roleFeaturesSet).map(tuple3 -> {
            rolesDataDto.setRoles(tuple3.getT1());
            rolesDataDto.setUser(tuple3.getT2());
            Map<Long, RoleFeatureDto> featureMap = new HashMap<>();
            tuple3.getT3().forEach(rolePermission -> {
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
