package com.project.dto.response;

import java.util.List;

public record LikedCartResponseDto(Long id,
                                   Long userId,
                                   List<ProjectResponseDto> projectResponseDtos) {
}
