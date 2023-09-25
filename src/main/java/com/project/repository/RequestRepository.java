package com.project.repository;

import com.project.model.Project;
import com.project.model.Request;
import com.project.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Request r SET r.status = :status WHERE r.id = :id")
    void updateStatusById(@Param("id") Long id,
                          @Param("status") Request.Status status);

    List<Request> findAllByProjectId(Long id);

    @Query("SELECT r FROM Request r join fetch r.project WHERE r.project.owner.id = :ownerId")
    List<Request> findAllByProjectsOwnerId(@Param("ownerId") Long ownerId);

    boolean existsByProjectAndUserAndStatus(Project project, User user, Request.Status status);
}
