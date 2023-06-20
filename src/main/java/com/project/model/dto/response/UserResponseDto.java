package com.project.model.dto.response;

import com.project.model.Role;
import com.project.model.Speciality;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String provider;
    private LocalDate registrationDate;
    private String profileImage;
    private List<Role> roles;
    private Set<Speciality> specialities;
}
