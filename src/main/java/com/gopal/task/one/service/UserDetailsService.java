package com.gopal.task.one.service;

import com.gopal.task.one.dto.UserDetailsDto;
import com.gopal.task.one.model.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserDetailsService {

    Mono<UserDetails> getUserDetails(Long userId);

    Flux<String> getRolesOfUser(Long userId);
}
