package com.project.service;

import com.project.model.Speciality;
import java.util.List;

public interface SpecialityService {
    List<Speciality.SpecialityName> convertStringsToSpecialities(List<String> specialitiesNames);
}
