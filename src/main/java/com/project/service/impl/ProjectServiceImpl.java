package com.project.service.impl;

import com.project.model.Project;
import com.project.model.ProjectStatus;
import com.project.repository.ProjectRepository;
import com.project.service.ProjectService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project getById(Long id) {
        return projectRepository.getById(id);
    }

    @Override
    public List<Project> findAll(PageRequest pageRequest) {
        return projectRepository.findAll(pageRequest).toList();
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> findAllByIdIn(List<Long> ids) {
        return projectRepository.findAllByIdIn(ids);
    }

    @Override
    public Project changeStatus(Project project, String status) {
        project.setProjectStatus(ProjectStatus.Status.valueOf(status));
        return save(project);
    }
}
