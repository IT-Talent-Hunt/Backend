package com.project.model.dto.request;

import com.project.validation.FieldsValueMatch;
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
    private String email;
    private String password;
    private String confirmPassword;
}
