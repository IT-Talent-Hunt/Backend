package com.project.service;

import com.project.model.Team;
import java.util.List;

public interface TeamService {
    Team getById(Long id);

    List<Team> getAll();

    Team save(Team team);

    void deleteById(Long id);
}
