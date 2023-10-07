package com.gopal.task.one.controller;

import com.gopal.task.one.dto.UserDetailsDto;
import com.gopal.task.one.dto.UserRolesDataDto;
import com.gopal.task.one.mapper.UserMapper;
import com.gopal.task.one.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsService userService;

    private final UserMapper mapper;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDetailsDto>> getUserDetails(@PathVariable Long id) {
        return userService.getUserDetails(id).
                map(user -> ResponseEntity.ok(mapper.userEntityToDto(user)));
    }

    @GetMapping("/{id}/roles")
    public Mono<ResponseEntity<Set<String>>> getRolesOfUser(@PathVariable Long id) {
        return userService.getRolesOfUser(id).collect(Collectors.toSet()).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}/feature-set")
    public Mono<ResponseEntity<UserRolesDataDto>> getFeaturesOfUser(@PathVariable Long id) {
        return userService.getRoleDetailsOfUser(id).map(ResponseEntity::ok);
    }

}
