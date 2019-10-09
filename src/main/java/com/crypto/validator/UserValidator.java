package com.crypto.validator;

import com.crypto.dto.UserDTO;
import com.crypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private static final String NOT_EMPTY = "NotEmpty";

    @Autowired
    private UserService userService;

    @Autowired
    private EmailValidator emailValidator;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO user = (UserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", NOT_EMPTY);
        if (userService.isUserWithEmailExists(user.getEmail())) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }
        emailValidator.validate(user.getEmail(), errors);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", NOT_EMPTY);
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.userForm.passwordConfirm");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", NOT_EMPTY);

    }
}
