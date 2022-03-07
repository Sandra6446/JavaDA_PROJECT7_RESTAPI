package com.nnk.springboot.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint,String> {

    @Override
    public void initialize(PasswordConstraint password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cxt) {
        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[A-Z])"
                + "(?=.*[#?!@$%^&*-])"
                + "(?=\\S+$).{8,20}$";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);
        return m.matches();
    }

}
