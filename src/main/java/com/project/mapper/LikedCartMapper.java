package com.project.mapper;

import com.project.dto.request.LikedCartRequestDto;
import com.project.dto.response.LikedCartResponseDto;
import com.project.model.LikedCart;
import com.project.model.Project;
import com.project.service.ProjectService;
import com.project.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserService.class, ProjectService.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        disableSubMappingMethodsGeneration = true,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface LikedCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "projects", target = "projectsIds", qualifiedByName = "projectsToIds")
    LikedCartResponseDto modelToDto(LikedCart likedCart);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "projectsIds", target = "projects")
    LikedCart dtoToModel(LikedCartRequestDto likedCartRequestDto);

    @Named("projectsToIds")
    default List<Long> projectsToIds(List<Project> projects) {
        return projects.stream()
                .mapToLong(Project::getId)
                .boxed()
                .collect(Collectors.toList());
    }
}
