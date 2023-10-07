package com.gopal.task.one.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Feature {

    @Id
    private Long featureId;

    private String featureName;

    private String featureDescription;
}
