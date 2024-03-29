package com.project.mapper;

import com.project.dto.request.RequestModelRequestDto;
import com.project.dto.response.RequestResponseDto;
import com.project.model.Request;
import com.project.service.ProjectService;
import com.project.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(uses = {ProjectService.class, UserService.class,
        ProjectMapper.class, UserMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        disableSubMappingMethodsGeneration = true,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface RequestMapper {
    @Mapping(source = "user", target = "userResponseDto")
    @Mapping(source = "project", target = "projectResponseDto")
    RequestResponseDto modelToDto(Request request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "message", target = "message")
    Request dtoToModel(RequestModelRequestDto requestModelRequestDto);
}
