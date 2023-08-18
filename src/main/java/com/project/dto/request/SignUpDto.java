package com.project.dto.request;

import com.project.validation.FieldsValueMatch;
import com.project.validation.ValidEmail;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    @NotBlank
    private String confirmPassword;
}
