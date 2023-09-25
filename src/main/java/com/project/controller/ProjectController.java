package com.project.controller;

import com.project.dto.request.ProjectRequestDto;
import com.project.dto.request.ProjectSearchParameters;
import com.project.dto.response.ProjectResponseDto;
import com.project.dto.response.ProjectsSearchResponseDto;
import com.project.mapper.ProjectMapper;
import com.project.model.Project;
import com.project.service.ProjectService;
import com.project.util.PageRequestUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/search")
    public ProjectsSearchResponseDto search(ProjectSearchParameters params,
                                            @RequestParam(defaultValue = "20") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pageRequestUtil
                .getPageRequest(count, page, sortBy, "id", "name", "creationDate", "status");
        Page<Project> projectPage = projectService.search(params, pageRequest);
        List<ProjectResponseDto> projectList = projectPage.stream()
                .map(projectMapper::modelToDto).toList();
        return new ProjectsSearchResponseDto(
                projectList, projectPage.getTotalPages(), projectPage.getTotalElements());
    }

    @GetMapping("/{id}")
    public ProjectResponseDto getById(@PathVariable Long id) {
        return projectMapper.modelToDto(projectService.getById(id));
    }

    @GetMapping("/by-user/{userId}")
    public List<ProjectResponseDto> findAllOwnedByUserId(@PathVariable Long userId) {
        return projectService.findAllOwnedByOwnerId(userId)
                .stream()
                .map(projectMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-user/{userId}/status")
    public List<ProjectResponseDto> findAllByUserIdAndProjectStatus(
            @PathVariable Long userId,
            @RequestParam String projectStatus) {
        return projectService.findAllByUserIdAndProjectStatus(userId,
                        Project.Status.valueOf(projectStatus))
                .stream()
                .map(projectMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    private ProjectResponseDto save(@RequestBody ProjectRequestDto projectRequestDto) {
        return projectMapper.modelToDto(
                projectService.save(
                        projectMapper.dtoToModel(projectRequestDto)));
    }

    @PutMapping("/{id}/teams/{teamId}")
    public ProjectResponseDto update(@PathVariable Long id,
                                     @PathVariable Long teamId,
                                     @RequestBody ProjectRequestDto projectRequestDto) {
        LocalDateTime creationDate = projectService.getById(id).getCreationDate();
        Project project = projectMapper.dtoToModel(projectRequestDto);
        project.setId(id);
        project.getTeam().setId(teamId);
        project.setCreationDate(creationDate);
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
    public ProjectResponseDto deleteById(@PathVariable Long id) {
        ProjectResponseDto projectResponseDto =
                projectMapper.modelToDto(projectService.getById(id));
        projectService.deleteById(id);
        return projectResponseDto;
    }
}
