package com.gopal.task.one.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
