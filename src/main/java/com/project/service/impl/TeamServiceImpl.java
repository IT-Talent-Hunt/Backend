package com.project.service.impl;

import com.project.exception.UserNotFoundException;
import com.project.model.Team;
import com.project.model.User;
import com.project.repository.TeamRepository;
import com.project.repository.UserRepository;
import com.project.service.TeamService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Can`t find team by id" + id));
    }

    @Override
    public List<Team> findAll(PageRequest pageRequest) {
        return teamRepository.findAll(pageRequest).toList();
    }

    @Override
    public Team addUser(Long id, Long userId) {
        Team team = findById(id);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Can't find user with id: " + userId));
        if (team.getRequiredSpecialities().contains(user.getSpeciality())) {
            addUserToTeam(team, user);
            removeSpeciality(team.getRequiredSpecialities(), user);
        } else {
            throw new NoSuchElementException("Team are not require user with this speciality: "
                    + user.getSpeciality());
        }
        return teamRepository.save(team);
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

    private void removeSpeciality(List<User.Speciality> teamSpecialities, User user) {
        teamSpecialities.remove(user.getSpeciality());
    }

    private void addUserToTeam(Team team, User user) {
        List<User> users = team.getUsers();
        users.add(user);
        team.setUsers(users);
    }
}
