package com.project.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikedCartRequestDto {
    private Long userId;
    private List<Long> projectsIds;
}
