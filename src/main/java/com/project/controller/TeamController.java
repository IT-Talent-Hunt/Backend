package com.project.controller;

import com.project.dto.request.TeamRequestDto;
import com.project.dto.response.TeamResponseDto;
import com.project.mapper.TeamMapper;
import com.project.model.Team;
import com.project.service.TeamService;
import com.project.util.PageRequestUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final PageRequestUtil pageRequestUtil;

    @GetMapping
    private List<TeamResponseDto> findAll(@RequestParam(defaultValue = "20") Integer count,
                                           @RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pageRequestUtil
                .getPageRequest(count, page, sortBy, "id");
        return teamService.findAll(pageRequest)
                .stream()
                .map(teamMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private TeamResponseDto getById(@PathVariable Long id) {
        return teamMapper.modelToDto(teamService.getById(id));
    }

    @PostMapping("/add-speciality")
    public TeamResponseDto addRequiredSpeciality(@PathVariable Long id,
                                                 @RequestParam String speciality) {
        return teamMapper.modelToDto(
                teamService.addSpeciality(teamService.getById(id), speciality));
    }

    @PostMapping
    private TeamResponseDto save(@RequestBody TeamRequestDto teamRequestDto) {
        return teamMapper.modelToDto(
                teamService.save(
                        teamMapper.dtoToModel(teamRequestDto)));
    }

    @PutMapping("/{id}")
    public TeamResponseDto update(@PathVariable Long id,
                                      @RequestBody TeamRequestDto teamRequestDto) {
        Team team = teamMapper.dtoToModel(teamRequestDto);
        team.setId(id);
        return teamMapper.modelToDto(teamService.save(team));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        teamService.deleteById(id);
    }
}
