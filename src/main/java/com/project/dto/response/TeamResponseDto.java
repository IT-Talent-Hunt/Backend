package com.project.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponseDto {
    private Long id;
    private List<Long> usersIds;
    private List<String> specialitiesNames;
}
