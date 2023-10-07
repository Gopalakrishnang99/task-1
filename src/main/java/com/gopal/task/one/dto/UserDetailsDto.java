package com.gopal.task.one.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDetailsDto {

    private Long userId;

    private String email;

    private String firstName;

    private String lastName;

    private Set<String> roles;
}
