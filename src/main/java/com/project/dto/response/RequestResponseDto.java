package com.project.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RequestResponseDto {
    private Long id;
    private Long userId;
    private Long projectId;
    private String message;
    private String status;
}
