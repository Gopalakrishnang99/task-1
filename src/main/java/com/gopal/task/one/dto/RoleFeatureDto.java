package com.gopal.task.one.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the feature data of a user
 */
@Data
public class RoleFeatureDto {

    public RoleFeatureDto(Long id, String name) {
        this.id = id;
        this.name = name;
        this.permission = new HashSet<>();
    }

    private Long id;

    private String name;

    private Set<String> permission;
}
