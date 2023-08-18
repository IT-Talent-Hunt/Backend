package com.project.repository.project;

import com.project.model.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>,
        JpaSpecificationExecutor<Project> {
    @Query("SELECT DISTINCT p FROM Project p JOIN p.team t JOIN t.users u "
            + "WHERE u.id = :userId AND p.status = :projectStatus")
    List<Project> findAllProjectsByUserIdAndProjectStatus(
            @Param("userId") Long userId,
            @Param("projectStatus") Project.Status projectStatus);

    List<Project> findAllByIdIn(List<Long> ids);

    List<Project> findAllByOwnerId(Long id);

    List<Project> findAllByOwnerIdAndStatus(Long userId, Project.Status projectStatus);
}
