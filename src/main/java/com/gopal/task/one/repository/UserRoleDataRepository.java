package com.gopal.task.one.repository;

import com.gopal.task.one.model.RolePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class UserRoleDataRepository {

    private final R2dbcEntityTemplate template;

    private final FeatureRepository featureRepository;

    private final PermissionRepository permissionRepository;

    public Flux<RolePermission> getRoleFeatures(Long roleId) {
        Flux<RolePermission> rolePermission = template.select(Query.query(Criteria.where("role_id").is(roleId)), RolePermission.class);
        return rolePermission.flatMap(role -> {
                    return featureRepository.findById(role.getFeatureId()).map(feature -> {
                        role.setFeature(feature);
                        return role;
                    }).subscribeOn(Schedulers.parallel());
                }
        ).flatMap(role -> {
            return permissionRepository.findById(role.getPermissionId()).map(permission -> {
                role.setPermission(permission);
                return role;
            }).subscribeOn(Schedulers.parallel());
        });
    }

}
