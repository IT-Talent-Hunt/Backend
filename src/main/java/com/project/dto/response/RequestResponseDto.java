package com.project.dto.response;

public record RequestResponseDto(Long id, UserResponseDto userResponseDto,
                                 ProjectResponseDto projectResponseDto,
                                 String message, String status) {
}
