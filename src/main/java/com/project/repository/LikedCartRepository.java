package com.project.repository;

import com.project.model.LikedCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedCartRepository extends JpaRepository<LikedCart, Long> {
    LikedCart getByUserId(Long id);
}
