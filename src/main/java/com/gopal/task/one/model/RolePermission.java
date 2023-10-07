package com.gopal.task.one.model;

import lombok.Data;

@Data
public class RolePermission {

    private Long roleId;

    private Long featureId;

    private Long permissionId;
}
