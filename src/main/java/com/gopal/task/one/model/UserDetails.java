package com.gopal.task.one.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * DB entity representing a user
 */
@Data
@Table("user_details")
public class UserDetails {

    @Id
    private Long userId;

    private String email;

    private String firstName;

    private String lastName;
}
