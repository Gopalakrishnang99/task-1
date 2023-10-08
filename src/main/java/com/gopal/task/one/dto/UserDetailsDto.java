package com.gopal.task.one.dto;

import lombok.Data;

import java.util.Set;

/**
 * Represents a user
 */
@Data
public class UserDetailsDto {

    private Long userId;

    private String email;

    private String firstName;

    private String lastName;
}
