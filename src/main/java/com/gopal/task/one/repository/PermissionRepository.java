package com.gopal.task.one.repository;

import com.gopal.task.one.model.Permission;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PermissionRepository extends ReactiveCrudRepository<Permission,Long> {
}
