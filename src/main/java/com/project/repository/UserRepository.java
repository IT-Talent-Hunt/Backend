package com.project.repository;

import com.project.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select distinct u from User u left join fetch u.roles where u.email = ?1")
    Optional<User> findByEmailFetchRoles(String email);
}
