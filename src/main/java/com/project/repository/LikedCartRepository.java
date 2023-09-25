package com.project.repository;

import com.project.model.LikedCart;
import com.project.model.Project;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedCartRepository extends JpaRepository<LikedCart, Long> {
    LikedCart getByUserId(Long userId);

    @Query("SELECT p FROM LikedCart LC JOIN LC.projects p WHERE LC.user.id = :userId")
    Page<Project> findProjectsByUserId(@Param("userId") Long userId, Pageable pageable);

    List<LikedCart> findByProjectsContaining(Project project);
}
