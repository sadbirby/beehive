package com.commons.response.constant;

public class ValidationRule {
    public static final String PASSWORD_VALIDATION_REGEX = "^(?=.{8,72}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{6,72}$";
    public static final String PASSWORD_VALIDATION_MESSAGE = "Password must be 8-72 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*?&)";
    public static final String EMAIL_VALIDATION_MESSAGE = "Email address must be a valid email address";
    public static final String USERNAME_VALIDATION_MESSAGE = "Username must be between 4 to 64 characters long";
}
