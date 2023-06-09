package com.project.mapper;

import com.project.dto.request.TeamRequestDto;
import com.project.dto.response.TeamResponseDto;
import com.project.model.Speciality;
import com.project.model.Team;
import com.project.model.User;
import com.project.service.SpecialityService;
import com.project.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserService.class, SpecialityService.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        disableSubMappingMethodsGeneration = true,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface TeamMapper {
    @Mapping(source = "users", target = "usersIds", qualifiedByName = "usersToIds")
    @Mapping(source = "specialities", target = "specialitiesNames",
            qualifiedByName = "specialitiesToStrings")
    TeamResponseDto modelToDto(Team team);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "usersIds", target = "users")
    @Mapping(source = "specialitiesNames", target = "specialities")
    Team dtoToModel(TeamRequestDto teamRequestDto);

    @Named("usersToIds")
    default List<Long> usersToIds(List<User> users) {
        return users.stream()
                .mapToLong(User::getId)
                .boxed()
                .collect(Collectors.toList());
    }

    @Named("specialitiesToStrings")
    default List<String> specialitiesToStrings(List<Speciality.SpecialityName> specialities) {
        return specialities.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
