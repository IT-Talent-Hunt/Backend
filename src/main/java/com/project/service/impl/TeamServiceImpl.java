package com.project.service.impl;

import com.project.model.Team;
import com.project.model.User;
import com.project.repository.TeamRepository;
import com.project.service.TeamService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team getById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Can`t find team by id" + id));
    }

    @Override
    public List<Team> findAll(PageRequest pageRequest) {
        return teamRepository.findAll(pageRequest).toList();
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Team addSpeciality(Team team, String speciality) {
        User.Speciality specialityName = User.Speciality.fromValue(speciality);
        team.getRequiredSpecialities().add(specialityName);
        return save(team);
    }
}
