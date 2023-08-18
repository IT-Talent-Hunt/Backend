package com.project.service;

import com.project.model.Team;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface TeamService {
    Team getById(Long id);

    List<Team> findAll(PageRequest pageRequest);

    Team save(Team team);

    void deleteById(Long id);

    Team addSpeciality(Team team, String speciality);
}
