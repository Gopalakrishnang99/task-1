package com.gopal.task.one.service.impl;

import com.gopal.task.one.model.Role;
import com.gopal.task.one.model.UserDetails;
import com.gopal.task.one.repository.UserDetailsRepository;
import com.gopal.task.one.repository.UserRoleRepository;
import com.gopal.task.one.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userRepo;

    private final UserRoleRepository userRoleRepo;

    private final R2dbcEntityTemplate template;

    @Override
    @Transactional
    public Mono<UserDetails> getUserDetails(Long userId) {
        return userRepo.findById(userId);
    }

    @Override
    @Transactional
    public Flux<String> getRolesOfUser(Long userId) {
        return userRoleRepo.findAllByUserId(userId)
                .flatMap(role ->
                        template.selectOne(Query.query(Criteria.where("role_id").is(role.getRoleId())),
                                Role.class))
                .map(Role::getRoleName);
    }
}
