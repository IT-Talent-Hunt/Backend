package com.project.dto.request;

import com.project.validation.FieldsValueMatch;
import com.project.validation.ValidEmail;
import lombok.Getter;
import lombok.Setter;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "Passwords do not match!"
)
@Getter
@Setter
public class SignUpDto {
    @ValidEmail
    private String email;
    private String password;
    private String confirmPassword;
}
