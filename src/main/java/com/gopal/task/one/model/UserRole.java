package com.gopal.task.one.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
public class UserRole {

    @Id
    private Long roleId;

    private String roleName;

    private String roleDescription;

}
