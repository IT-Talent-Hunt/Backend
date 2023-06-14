package com.project.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProjectResponseDto {
    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private Long ownerId;
    private String description;
    private Long teamId;
    private String socialLinks;
    private String status;
}
