package com.project.mapper;

import com.project.dto.request.ProjectRequestDto;
import com.project.dto.response.ProjectResponseDto;
import com.project.model.Project;
import com.project.service.TeamService;
import com.project.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserService.class, TeamService.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        disableSubMappingMethodsGeneration = true,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface ProjectMapper {
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "projectStatus", target = "status")
    ProjectResponseDto modelToDto(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "projectStatus", ignore = true)
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "teamId", target = "team")
    Project dtoToModel(ProjectRequestDto projectRequestDto);
}
