package com.gopal.task.one.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

/**
 * DB entity representing a feature of a role
 */
@Data
@Table("feature")
public class Feature {

    @Id
    private Long featureId;

    private String featureName;

    private String description;

}
