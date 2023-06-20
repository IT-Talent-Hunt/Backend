package com.project.repository;

import com.project.model.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByIdIn(List<Long> ids);
}
