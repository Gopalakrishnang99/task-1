package com.gopal.task.one.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RoleFeatureDto {

    private Long id;

    private String name;

    private Set<String> permission;
}
