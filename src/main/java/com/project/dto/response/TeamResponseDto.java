package com.project.dto.response;

import java.util.List;

public record TeamResponseDto(Long id, List<UserResponseDto> userResponseDtos,
                              List<String> requiredSpecialities,
                              List<Long> requestIds, int maxMembers) {
}
