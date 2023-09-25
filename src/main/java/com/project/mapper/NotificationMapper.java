package com.project.mapper;

import com.project.dto.response.NotificationResponseDto;
import com.project.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(uses = {UserMapper.class, ProjectMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        disableSubMappingMethodsGeneration = true,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface NotificationMapper {

    @Mapping(source = "user", target = "userResponseDto")
    @Mapping(source = "request.id", target = "requestId")
    NotificationResponseDto modelToDto(Notification notification);
}
