package com.gopal.task.one.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("role_permission_mapping")
public class RolePermission {

    private Long roleId;

    private Long featureId;

    private Long permissionId;
}
