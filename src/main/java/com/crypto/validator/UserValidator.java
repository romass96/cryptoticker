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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Email can't be empty");
        if (userService.isUserWithEmailExists(user.getEmail())) {
            errors.rejectValue("email", "User with this email exists");
        }
        emailValidator.validate(user.getEmail(), errors);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Password can't be empty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Password must have length 8-32 symbols");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Passwords don't match");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "First name can't be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Last name can't be empty");

    }
}
