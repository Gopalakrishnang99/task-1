package com.gopal.task.one.repository;

import com.gopal.task.one.model.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RoleRepository extends ReactiveCrudRepository<Role,Long> {
}
