package com.gopal.task.one.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private  final WebFluxProperties fluxProperties;

    private final ServerProperties serverProperties;

    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(buildSelfBaseUrl()).build();
    }

    private String buildSelfBaseUrl(){
        return "http://localhost:" + serverProperties.getPort() +
                fluxProperties.getBasePath();
    }
}
