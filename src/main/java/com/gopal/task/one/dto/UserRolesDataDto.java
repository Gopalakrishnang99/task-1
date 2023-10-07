package com.gopal.task.one.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserRolesDataDto {

    private UserDetailsDto user;

    private Set<String> roles;

    private Set<RoleFeatureDto> features;
}
