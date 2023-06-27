package com.project.controller;

import com.project.dto.request.ProjectRequestDto;
import com.project.dto.response.ProjectResponseDto;
import com.project.dto.response.RequestResponseDto;
import com.project.mapper.ProjectMapper;
import com.project.model.Project;
import com.project.service.ProjectService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @GetMapping
    public List<ProjectResponseDto> getAll() {
        return projectService.getAll().stream()
                .map(projectMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private ProjectResponseDto getProjectById(@PathVariable Long id) {
        return projectMapper.modelToDto(projectService.getById(id));
    }

    @PostMapping
    private ProjectResponseDto save(@RequestBody ProjectRequestDto projectRequestDto) {
        return projectMapper.modelToDto(
                projectService.save(
                        projectMapper.dtoToModel(projectRequestDto)));
    }

    @PutMapping("/{id}")
    public ProjectResponseDto updateProject(@PathVariable Long id,
                                      @RequestBody ProjectRequestDto projectRequestDto) {
        Project project = projectMapper.dtoToModel(projectRequestDto);
        project.setId(id);
        return projectMapper.modelToDto(projectService.save(project));
    }

    @PostMapping("/change-status/{id}")
    public ProjectResponseDto changeStatus(@PathVariable Long id,
                                           @RequestParam String status) {
        return projectMapper.modelToDto(
                projectService.changeStatus(
                        projectService.getById(id), status));
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        Project team = projectService.getById(id);
        if (team != null) {
            projectService.deleteById(id);
        }
    }
}
