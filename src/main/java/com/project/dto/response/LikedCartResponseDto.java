package com.project.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LikedCartResponseDto {
    private Long id;
    private Long userId;
    private List<Long> projectsIds;
}
