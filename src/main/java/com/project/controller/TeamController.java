package com.project.controller;

import com.project.dto.request.TeamRequestDto;
import com.project.dto.response.TeamResponseDto;
import com.project.mapper.TeamMapper;
import com.project.model.Team;
import com.project.service.TeamService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping
    private List<TeamResponseDto> getTeams() {
        return teamService.getAll().stream()
                .map(teamMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private TeamResponseDto getTeamById(@PathVariable Long id) {
        return teamMapper.modelToDto(teamService.getById(id));
    }

    @PostMapping
    private TeamResponseDto save(@RequestBody TeamRequestDto teamRequestDto) {
        return teamMapper.modelToDto(
                teamService.save(
                        teamMapper.dtoToModel(teamRequestDto)));
    }

    @PutMapping("/{id}")
    public TeamResponseDto updateTeam(@PathVariable Long id,
                                    @RequestBody TeamRequestDto teamRequestDto) {
        Team team = teamMapper.dtoToModel(teamRequestDto);
        team.setId(id);
        return teamMapper.modelToDto(teamService.save(team));
    }

    @DeleteMapping("/{id}")
    public TeamResponseDto deleteTeam(@PathVariable Long id) {
        Team team = teamService.getById(id);
        if (team != null) {
            teamService.deleteById(id);
            return teamMapper.modelToDto(team);
        }
        return null;
    }
}
