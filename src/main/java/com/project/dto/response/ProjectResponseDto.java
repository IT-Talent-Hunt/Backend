package com.project.dto.response;

import com.project.model.SocialLink;
import java.time.LocalDateTime;

public record ProjectResponseDto(Long id, String name, LocalDateTime creationDate,
                                 Long ownerId, String description, TeamResponseDto teamResponseDto,
                                 SocialLink socialLink, String status){
}
