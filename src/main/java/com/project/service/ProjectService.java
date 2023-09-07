package com.project.service;

import com.project.dto.request.ProjectSearchParameters;
import com.project.model.Project;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProjectService {
    Project getById(Long id);

    List<Project> findAll(PageRequest pageRequest);

    Page<Project> search(ProjectSearchParameters params, PageRequest pageRequest);

    Project save(Project project);

    void deleteById(Long id);

    List<Project> findAllByIdIn(List<Long> ids);

    List<Project> findAllByUserIdAndProjectStatus(Long userId, Project.Status status);

    List<Project> findAllOwnedByOwnerId(Long id);

    Project changeStatus(Project project, String status);
}
