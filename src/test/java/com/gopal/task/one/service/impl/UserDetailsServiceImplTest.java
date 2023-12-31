package com.gopal.task.one.service.impl;

import com.gopal.task.one.exception.UserNotFoundException;
import com.gopal.task.one.mapper.UserMapper;
import com.gopal.task.one.repository.RoleRepository;
import com.gopal.task.one.repository.UserDetailsRepository;
import com.gopal.task.one.repository.UserRoleDataRepository;
import com.gopal.task.one.repository.UserRoleRepository;
import com.gopal.task.one.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    UserDetailsRepository userRepo;

    @Mock
    UserRoleRepository userRoleRepo;

    @Mock
    RoleRepository roleRepo;

    @Mock
    UserRoleDataRepository userRoleDataRepo;

    @Mock
    WebClient webClient;

    @Spy
    UserMapper userMapper;

    @Mock
    ExchangeFunction exchangeFunction;

    UserDetailsServiceImpl userService;

    @BeforeEach
    public void setUpMockWebClient() {
        webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build();
        userService = new UserDetailsServiceImpl(userRepo, userRoleRepo, roleRepo, userRoleDataRepo, webClient, userMapper);
    }

    @Test
    @DisplayName("Get user details - user exists")
    void getUserDetails() {
        Mockito.when(userRepo.findById(Mockito.anyLong()))
                .thenAnswer(i -> Mono.just(TestUtil.getTestUserDetails(i.getArgument(0))));
        StepVerifier.create(userService.getUserDetails(1L))
                .consumeNextWith(user -> {
                    Assertions.assertEquals(1L, (long) user.getUserId());
                    Assertions.assertEquals(user.getEmail(), "email@email.com");
                    Assertions.assertEquals(user.getFirstName(), "first_name");
                    Assertions.assertEquals(user.getLastName(), "last_name");
                }).verifyComplete();
    }

    @Test
    @DisplayName("Get user details - user does not exist")
    void getUserDetailsWhenUserDoesNotExist() {
        Mockito.when(userRepo.findById(Mockito.anyLong()))
                .thenAnswer(i -> Mono.error(new UserNotFoundException("User not found")));
        StepVerifier.create(userService.getUserDetails(1L))
                .expectErrorMatches(exc -> exc instanceof UserNotFoundException &&
                        exc.getMessage().equals("User not found")).verify();
    }

    @Test
    @DisplayName("Test to get roles of a user")
    void getRolesOfUser() {
        Mockito.when(userRoleRepo.findAllByUserId(Mockito.anyLong()))
                .thenAnswer(i ->
                        Flux.just(
                                TestUtil.getUserRole(i.getArgument(0), 2L),
                                TestUtil.getUserRole(i.getArgument(0), 3L)
                        )
                );
        Mockito.when(roleRepo.findById(Mockito.anyLong()))
                .thenAnswer(i -> Mono.just(TestUtil.getRoleEntity(i.getArgument(0))));
        StepVerifier.create(userService.getRolesOfUser(1L))
                .expectNext("admin")
                .expectNext("user")
                .verifyComplete();
    }

    @Test
    @DisplayName("Test to get roles of a user - roles/user does not exist")
    void getRolesOfUserWhenUserOrRolesDoesNotExist() {
        Mockito.when(userRoleRepo.findAllByUserId(Mockito.anyLong()))
                .thenReturn(Flux.empty());
        StepVerifier.create(userService.getRolesOfUser(1L))
                .expectNextCount(0L)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test to get feature set of user")
    void getFeatureSetOfUser() {
        Mockito.when(exchangeFunction.exchange(Mockito.any(ClientRequest.class)))
                .thenReturn(
                        Mono.just(ClientResponse.create(HttpStatus.OK)
                                .header("content-type", "application/json")
                                .body("[\"admin\"]").build())
                );
        Mockito.when(userRepo.findById(Mockito.anyLong()))
                .thenAnswer(i -> Mono.just(TestUtil.getTestUserDetails(i.getArgument(0))));
        Mockito.when(userRoleRepo.findAllByUserId(Mockito.anyLong()))
                .thenAnswer(i ->
                        Flux.just(
                                TestUtil.getUserRole(i.getArgument(0), 2L),
                                TestUtil.getUserRole(i.getArgument(0), 3L)
                        )
                );
        Mockito.when(userRoleDataRepo.getRoleFeatures(Mockito.anyLong()))
                .thenAnswer(i -> Flux.just(TestUtil.getRolePermission(i.getArgument(0))));
        StepVerifier.create(userService.getRoleDetailsOfUser(1L))
                .consumeNextWith(userRolesDataDto -> {
                    Assertions.assertEquals(userRolesDataDto.getFeatures().size(),1);
                    Assertions.assertEquals(userRolesDataDto.getUser().getEmail(),"email@email.com");
                })
                .verifyComplete();
    }
}