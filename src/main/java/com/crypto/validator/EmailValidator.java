package com.crypto.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements Validator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(String.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String email = (String) o;
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            errors.rejectValue("email", "Email isn't valid");
        }
    }
}
