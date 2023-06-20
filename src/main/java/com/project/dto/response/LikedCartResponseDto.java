package com.project.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikedCartResponseDto {
    private Long id;
    private Long userId;
    private List<Long> projectsIds;
}
