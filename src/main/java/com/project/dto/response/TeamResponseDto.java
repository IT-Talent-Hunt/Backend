package com.project.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TeamResponseDto {
    private Long id;
    private List<Long> usersIds;
}
