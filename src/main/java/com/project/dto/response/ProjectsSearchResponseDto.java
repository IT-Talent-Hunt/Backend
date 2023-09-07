package com.project.dto.response;

import java.util.List;

public record ProjectsSearchResponseDto(List<ProjectResponseDto> projectResponseDtos,
                                        int totalPage) {
}
