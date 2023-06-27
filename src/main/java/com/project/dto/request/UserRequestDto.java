package com.project.dto.request;

import com.project.model.Role;
import com.project.model.Speciality;
import com.project.validation.ValidEmail;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserRequestDto {
    private String firstName;
    private String lastName;
    @ValidEmail
    private String email;
    private String password;
    private String profileImage;
    private Set<Speciality> specialities;
    private List<Role> roles;

}
