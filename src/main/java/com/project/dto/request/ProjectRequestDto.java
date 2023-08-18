package com.project.dto.request;

import com.project.model.SocialLink;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDto {
    private String name;
    private Long ownerId;
    private String description;
    private TeamRequestDto teamRequestDto;
    private SocialLink socialLink;
    private String status;
}
