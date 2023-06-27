package com.project.dto.request;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDto {
    private String name;
    private Long ownerId;
    private String description;
    private List<String> requiredSpeciality;
    private Map<String, String> socialLinks;
}
