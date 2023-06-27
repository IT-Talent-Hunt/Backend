package com.project.service;

import com.project.model.Speciality;
import com.project.model.Team;
import com.project.model.User;
import java.util.List;

public interface TeamService {
    Team getById(Long id);

    List<Team> getAll();

    Team addUser(Team team, User user, Speciality.SpecialityName speciality);

    Team save(Team team);

    void deleteById(Long id);

    Team specialitiesNamesToSpecialities(List<String> specialityNames);

    Team addSpeciality(Team team, String speciality);
}
