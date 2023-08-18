package com.project.service.impl;

import com.project.dto.ProjectSearchParameters;
import com.project.model.Project;
import com.project.repository.project.ProjectRepository;
import com.project.repository.project.ProjectSpecificationBuilder;
import com.project.service.ProjectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectSpecificationBuilder projectSpecificationBuilder;

    @Override
    public Project getById(Long id) {
        return projectRepository.getById(id);
    }

    @Override
    public List<Project> findAll(PageRequest pageRequest) {
        return projectRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Project> search(ProjectSearchParameters params) {
        Specification<Project> projectSpecification = projectSpecificationBuilder.build(params);
        return projectRepository.findAll(projectSpecification);
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
    public List<Project> findAllByUserIdAndProjectStatus(Long userId, Project.Status status) {
        return projectRepository.findAllProjectsByUserIdAndProjectStatus(userId, status);
    }

    @Override
    public List<Project> findAllOwnedByOwnerId(Long id) {
        return projectRepository.findAllByOwnerId(id);
    }

    @Override
    public Project changeStatus(Project project, String status) {
        project.setStatus(Project.Status.valueOf(status));
        return save(project);
    }
}
