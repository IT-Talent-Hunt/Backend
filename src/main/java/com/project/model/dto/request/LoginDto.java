package com.project.model.dto.request;

import com.project.validation.ValidEmail;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @ValidEmail
    private String email;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
