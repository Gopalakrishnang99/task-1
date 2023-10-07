package com.gopal.task.one.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Permission {

    @Id
    private Long permissionId;

    private String permissionName;

    private String description;
}
