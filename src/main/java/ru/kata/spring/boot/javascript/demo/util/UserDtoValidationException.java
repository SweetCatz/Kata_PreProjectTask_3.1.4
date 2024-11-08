package ru.kata.spring.boot.javascript.demo.util;

import org.springframework.validation.FieldError;
import java.util.List;

public class UserDtoValidationException extends RuntimeException {
    private final List<FieldError> validationErrors;

    public UserDtoValidationException(List<FieldError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public List<FieldError> getValidationErrors() {
        return validationErrors;
    }
}
