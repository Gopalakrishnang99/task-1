package com.gopal.task.one.service;

import com.gopal.task.one.dto.UserDetailsDto;
import com.gopal.task.one.dto.UserRolesDataDto;
import com.gopal.task.one.model.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserDetailsService {

    Mono<UserDetailsDto> getUserDetails(Long userId);

    Flux<String> getRolesOfUser(Long userId);

    Mono<UserRolesDataDto> getRoleDetailsOfUser(Long user);
}
