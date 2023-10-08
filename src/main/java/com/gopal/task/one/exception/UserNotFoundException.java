package com.gopal.task.one.exception;

/**
 * Exception class when requested user is not present
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
