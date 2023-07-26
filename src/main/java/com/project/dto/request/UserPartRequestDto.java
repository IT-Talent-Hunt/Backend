package com.project.dto.request;

import com.project.model.Speciality;
import java.util.Set;
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
    private Set<Speciality> specialities;
}
