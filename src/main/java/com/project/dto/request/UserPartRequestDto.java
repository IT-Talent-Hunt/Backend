package com.project.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPartRequestDto {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String speciality;
}
