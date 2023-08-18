package com.project.dto.request;

import com.project.model.Role;
import com.project.model.SocialLink;
import com.project.validation.ValidEmail;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    private String description;
    private List<SocialLink> socialLinks;
    private String profileImage;
    private String speciality;
    private List<Role> roles;
    private String skills;

}
