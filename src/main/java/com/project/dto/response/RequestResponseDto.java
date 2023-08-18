package com.project.dto.response;

public record RequestResponseDto(Long id, Long userId, Long projectId,
                                  String message, String speciality, String status) {
}
