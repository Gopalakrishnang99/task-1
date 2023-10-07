package com.gopal.task.one.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user_role_mapping")
public class UserRole {

    @Id
    private Long id;

    private Long userId;

    private Long roleId;

    @Transient
    private UserDetails user;

    @Transient
    private Role role;
}
