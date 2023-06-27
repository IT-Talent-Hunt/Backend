package com.project.service.impl;

import com.project.model.Speciality;
import com.project.service.SpecialityService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SpecialityServiceImpl implements SpecialityService {
    @Override
    public List<Speciality.SpecialityName> convertStringsToSpecialities(
            List<String> specialitiesNames) {
        return specialitiesNames.stream()
                .map(Speciality.SpecialityName::valueOf)
                .collect(Collectors.toList());
    }
}
