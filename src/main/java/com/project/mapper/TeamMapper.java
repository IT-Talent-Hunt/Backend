package com.project.mapper;

import com.project.dto.request.TeamRequestDto;
import com.project.dto.response.TeamResponseDto;
import com.project.model.Team;
import com.project.model.User;
import com.project.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserService.class, UserMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        disableSubMappingMethodsGeneration = true,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface TeamMapper {
    @Mapping(source = "users", target = "userResponseDtos")
    @Mapping(source = "requiredSpecialities", target = "requiredSpecialities",
            qualifiedByName = "specialitiesToStrings")
    @Mapping(target = "maxMembers", source = "team", qualifiedByName = "goes")
    TeamResponseDto modelToDto(Team team);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userIds", target = "users")
    @Mapping(source = "requiredSpecialities", target = "requiredSpecialities",
            qualifiedByName = "specialitiesToEnum")
    Team dtoToModel(TeamRequestDto teamRequestDto);

    @Named("goes")
    default int goes(Team team) {
        return team.getMaxMembers();
    }

    @Named("specialitiesToStrings")
    default List<String> specialitiesToStrings(List<User.Speciality> specialities) {
        return specialities.stream()
                .map(User.Speciality::getValue)
                .collect(Collectors.toList());
    }

    @Named("specialitiesToEnum")
    default List<User.Speciality> specialitiesToEnum(List<String> specialitiesString) {
        return specialitiesString.stream()
                .map(User.Speciality::fromValue)
                .collect(Collectors.toList());
    }
}
