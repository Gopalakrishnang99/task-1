package com.gopal.task.one.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

/**
 * DB entity representing a permission available
 */
@Data
@Table("permission")
public class Permission {

    @Id
    private Long permissionId;

    private String permissionName;

    private String description;
}
