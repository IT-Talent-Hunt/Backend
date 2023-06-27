package com.project.service;

import com.project.model.Project;
import com.project.model.ProjectStatus;
import java.util.List;

public interface ProjectService {
    Project getById(Long id);

    List<Project> getAll();

    Project save(Project project);

    void deleteById(Long id);

    List<Project> findAllByIdIn(List<Long> ids);

    Project changeStatus(Project project, ProjectStatus.Status status);
}
