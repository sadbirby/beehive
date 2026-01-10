package com.beehive.authservice.model.request;

import com.commons.response.constant.ValidationRule;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @Email @NotBlank @Size(max = 256) String email,
        @NotBlank @Pattern(
                regexp = ValidationRule.PASSWORD_VALIDATION_REGEX,
                message = ValidationRule.PASSWORD_VALIDATION_MESSAGE
        ) String password,
        @NotBlank @Size(min = 4, max = 64) String displayName
) {
}
