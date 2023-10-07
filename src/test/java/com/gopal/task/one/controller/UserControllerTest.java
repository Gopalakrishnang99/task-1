package com.gopal.task.one.controller;

import com.gopal.task.one.dto.UserRolesDataDto;
import com.gopal.task.one.exception.UserNotFoundException;
import com.gopal.task.one.service.UserDetailsService;
import com.gopal.task.one.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UserController.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("Test to get user details - user exist")
    void getUserDetails() {
        Mockito.when(userDetailsService.getUserDetails(Mockito.anyLong()))
                .thenAnswer(i -> Mono.just(TestUtil.getTestUserDetailsDto(i.getArgument(0))));
        webClient.get().uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.userId").isNumber()
                .jsonPath("$.email").isEqualTo("email@email.com");
    }

    @Test
    @DisplayName("Test to get user details - user does not exist")
    void getUserDetailsUserNotExisting() {
        Mockito.when(userDetailsService.getUserDetails(Mockito.anyLong()))
                .thenReturn(Mono.error(new UserNotFoundException("User not found")));
        webClient.get().uri("/user/3").exchange().expectStatus().is4xxClientError();
    }

    @Test
    @DisplayName("Test to get roles of user")
    void getRolesOfUser() {
        Mockito.when(userDetailsService.getRolesOfUser(Mockito.anyLong())).thenReturn(Flux.just("admin", "user"));
        List<String> roles = webClient.get().uri("/user/2/roles")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {
                })
                .returnResult()
                .getResponseBody();
        Assertions.assertEquals(roles, List.of("admin", "user"));
    }

    @Test
    @DisplayName("Test to get roles of user - user does not exist")
    void getRolesOfUserWhenUserDoesNotExist() {
        Mockito.when(userDetailsService.getRolesOfUser(Mockito.anyLong())).thenReturn(Flux.just());
        List<String> roles = webClient.get().uri("/user/2/roles")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {
                })
                .returnResult()
                .getResponseBody();
        Assertions.assertEquals(roles.size(), 0);
    }

    @Test
    @DisplayName("Test to get user and features")
    void getFeaturesOfUser() {
        Mockito.when(userDetailsService.getRoleDetailsOfUser(Mockito.anyLong()))
                .thenAnswer(i -> Mono.just(TestUtil.getUserRoleDataDto(i.getArgument(0))));
        UserRolesDataDto response = webClient.get()
                .uri("/user/2/feature-set")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserRolesDataDto.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertEquals(response.getFeatures().size(),1);
        response.getFeatures().forEach(roleFeatureDto -> {
            Assertions.assertTrue(roleFeatureDto.getPermission().contains("read"));
        });
    }
}