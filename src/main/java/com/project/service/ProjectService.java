package com.project.service;

import com.project.model.Project;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface ProjectService {
    Project getById(Long id);

    List<Project> findAll(PageRequest pageRequest);

    Project save(Project project);

    void deleteById(Long id);

    List<Project> findAllByIdIn(List<Long> ids);

    Project changeStatus(Project project, String status);
}
