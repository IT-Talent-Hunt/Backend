package com.project.dto.response;

import com.project.model.Role;
import com.project.model.SocialLink;
import java.time.LocalDate;
import java.util.List;

public record UserResponseDto(Long id, String firstName, String lastName, String email,
                              String provider, LocalDate registrationDate, String profileImage,
                              String description, String skills, List<SocialLink> socialLinks,
                              List<Role> roles, String speciality) {
}
