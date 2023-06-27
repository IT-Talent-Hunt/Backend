package com.project.dto.response;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDto {
    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private Long ownerId;
    private String description;
    private Long teamId;
    private Map<String, String> socialLinks;
    private String status;
}
