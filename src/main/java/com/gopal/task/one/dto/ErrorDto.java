package com.gopal.task.one.dto;

import lombok.Data;

/**
 * Represents the error message sent for API
 */
@Data
public class ErrorDto {

    private int status;

    private String message;
}
