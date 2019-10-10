package com.crypto.util;

import org.springframework.validation.ObjectError;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ValidationResult<T> {
    private Optional<T> value;
    private List<ObjectError> errors  = Collections.emptyList();
    private boolean success;

    public Optional<T> getValue() {
        return value;
    }

    public void setValue(Optional<T> value) {
        this.value = value;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success && value.isPresent();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static <T> ValidationResult success(T value) {
        ValidationResult<T> validationResult = new ValidationResult<>();
        validationResult.setValue(Optional.of(value));
        validationResult.setSuccess(true);
        return validationResult;
    }

    public static <T> ValidationResult failed(List<ObjectError> errors) {
        ValidationResult<T> validationResult = new ValidationResult<>();
        validationResult.setValue(Optional.empty());
        validationResult.setErrors(errors);
        validationResult.setSuccess(false);
        return validationResult;
    }
}
