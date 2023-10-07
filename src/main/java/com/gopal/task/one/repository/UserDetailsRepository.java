package com.gopal.task.one.repository;

import com.gopal.task.one.model.UserDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserDetailsRepository extends ReactiveCrudRepository<UserDetails,Long> {
}
