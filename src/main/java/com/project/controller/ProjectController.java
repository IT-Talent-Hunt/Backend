package com.project.controller;

import com.project.dto.request.ProjectRequestDto;
import com.project.dto.response.ProjectResponseDto;
import com.project.mapper.ProjectMapper;
import com.project.model.Project;
import com.project.service.ProjectService;
import com.project.util.PageRequestUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final PageRequestUtil pageRequestUtil;

    @GetMapping
    public List<ProjectResponseDto> findAll(@RequestParam(defaultValue = "20") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pageRequestUtil
                .getPageRequest(count, page, sortBy, "id", "name", "creationDate", "status");
        return projectService.findAll(pageRequest).stream()
                .map(projectMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private ProjectResponseDto getById(@PathVariable Long id) {
        return projectMapper.modelToDto(projectService.getById(id));
    }

    @PostMapping
    private ProjectResponseDto save(@RequestBody ProjectRequestDto projectRequestDto) {
        return projectMapper.modelToDto(
                projectService.save(
                        projectMapper.dtoToModel(projectRequestDto)));
    }

    @PutMapping("/{id}")
    public ProjectResponseDto update(@PathVariable Long id,
                                      @RequestBody ProjectRequestDto projectRequestDto) {
        Project project = projectMapper.dtoToModel(projectRequestDto);
        project.setId(id);
        return projectMapper.modelToDto(projectService.save(project));
    }

    @PatchMapping("/{id}/status")
    public ProjectResponseDto changeStatus(@PathVariable Long id,
                                           @RequestParam String status) {
        return projectMapper.modelToDto(
                projectService.changeStatus(
                        projectService.getById(id), status));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        projectService.deleteById(id);
    }
}
