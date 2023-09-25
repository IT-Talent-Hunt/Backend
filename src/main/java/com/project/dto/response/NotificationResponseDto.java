package com.project.dto.response;

import java.time.LocalDateTime;

public record NotificationResponseDto(Long id, Long requestId, String message,
                                      UserResponseDto userResponseDto,
                                      LocalDateTime creationDate, boolean read) {
}
