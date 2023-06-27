package com.project.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequestDto {
    private List<Long> usersIds;
    private List<String> specialitiesNames;
}
