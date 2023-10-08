package com.gopal.task.one.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * DB entity representing a role
 */
@Data
@Table("role")
public class Role {

    @Id
    private Long roleId;

    private String roleName;

    private String description;

}
