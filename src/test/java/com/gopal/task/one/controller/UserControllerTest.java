package com.gopal.task.one.controller;

import com.gopal.task.one.mapper.UserMapper;
import com.gopal.task.one.service.UserDetailsService;
import com.gopal.task.one.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UserController.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private UserDetailsService userDetailsService;

    @SpyBean
    private UserMapper userMapper;

    @Test
    void getUserDetails() {
        Mockito.when(userDetailsService.getUserDetails(Mockito.anyLong()))
                .thenAnswer(i -> Mono.just(TestUtil.getTestUserDetails(i.getArgument(0))));
        webClient.get().uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.userId").isNumber()
                .jsonPath("$.email").isEqualTo("email@email.com");
    }

    @Test
    void getRolesOfUser() {
        Mockito.when(userDetailsService.getRolesOfUser(Mockito.anyLong())).thenReturn(Flux.just("admin","user"));
        List<String> roles = webClient.get().uri("/user/2/roles")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {})
                .returnResult()
                .getResponseBody();
        Assertions.assertEquals(roles,List.of("admin","user"));
    }

    @Test
    void getFeaturesOfUser() {
    }
}