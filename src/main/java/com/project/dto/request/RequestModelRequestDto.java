package com.project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestModelRequestDto {
    private Long userId;
    private Long projectId;
    private String message;
}
