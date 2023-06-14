package com.project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDto {
    private String name;
    private Long ownerId;
    private String description;
    private Long teamId;
    private String socialLinks;
}
