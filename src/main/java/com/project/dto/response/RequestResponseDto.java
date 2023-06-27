package com.project.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResponseDto {
    private Long id;
    private Long userId;
    private Long projectId;
    private String message;
    private String speciality;
    private String status;
}
