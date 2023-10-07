package com.gopal.task.one.repository;

import com.gopal.task.one.model.UserRole;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRoleRepository extends ReactiveCrudRepository<UserRole,Long> {

    Flux<UserRole> findAllByUserId(Long userId);
}
