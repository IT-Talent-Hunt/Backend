package com.project.mapper;

import com.project.dto.request.UserPartRequestDto;
import com.project.dto.request.UserRequestDto;
import com.project.dto.response.UserResponseDto;
import com.project.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        disableSubMappingMethodsGeneration = true,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface UserMapper {
    @Mapping(target = "speciality", source = "speciality.value")
    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "speciality", source = "speciality", qualifiedByName = "toSpeciality")
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profileImage", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "socialLinks", ignore = true)
    @Mapping(target = "speciality", source = "speciality", qualifiedByName = "toSpeciality")
    User toEntity(UserPartRequestDto userPartRequestDto);

    @Named("toSpeciality")
    default User.Speciality toSpeciality(String speciality) {
        return User.Speciality.fromValue(speciality);
    }
}
