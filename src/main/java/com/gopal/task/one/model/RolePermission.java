package com.gopal.task.one.model;

import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

/**
 * DB entity representing a role and feature permissions mapping
 */
@Data
@Table("role_permission_mapping")
public class RolePermission {

    private Long roleId;

    private Long featureId;

    private Long permissionId;

    @Transient
    private Role role;

    @Transient
    private Feature feature;

    @Transient
    private Permission permission;
}
