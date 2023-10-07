package com.gopal.task.one.repository;

import com.gopal.task.one.model.Feature;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FeatureRepository extends ReactiveCrudRepository<Feature,Long> {
}
