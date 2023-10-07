package com.gopal.task.one.aspect;

import com.gopal.task.one.dto.ErrorDto;
import com.gopal.task.one.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(UserNotFoundException exc) {
        ErrorDto err = new ErrorDto();
        err.setStatus(404);
        err.setMessage(exc.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}
