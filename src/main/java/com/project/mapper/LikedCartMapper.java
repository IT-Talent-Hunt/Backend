package com.project.mapper;

import com.project.dto.request.LikedCartRequestDto;
import com.project.dto.response.LikedCartResponseDto;
import com.project.model.LikedCart;
import com.project.service.ProjectService;
import com.project.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserService.class, ProjectService.class, ProjectMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        disableSubMappingMethodsGeneration = true,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface LikedCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "projects", target = "projectResponseDtos")
    LikedCartResponseDto modelToDto(LikedCart likedCart);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "projectsIds", target = "projects")
    LikedCart dtoToModel(LikedCartRequestDto likedCartRequestDto);
}
