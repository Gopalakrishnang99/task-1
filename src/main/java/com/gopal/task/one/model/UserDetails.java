package com.gopal.task.one.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
public class UserDetails {

    @Id
    private Long userId;

    private String email;

    private String firstName;

    private String lastName;
}
