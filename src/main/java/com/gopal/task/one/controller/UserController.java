package com.gopal.task.one.controller;

import com.gopal.task.one.dto.RoleFeatureDto;
import com.gopal.task.one.dto.UserDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDetailsDto>> getUserDetails(@PathVariable Long id){
        return null;
    }

    @GetMapping("/{id}/roles")
    public Mono<ResponseEntity<Set<String>>> getRolesOfUser(@PathVariable Long id){
        return null;
    }

    @GetMapping("/{id}/feature-set")
    public Mono<ResponseEntity<RoleFeatureDto>> getFeaturesOfUser(@PathVariable Long id){
        return null;
    }

}
