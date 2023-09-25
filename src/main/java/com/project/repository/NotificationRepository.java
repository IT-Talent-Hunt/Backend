package com.project.repository;

import com.project.model.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.read = true WHERE n.id = ?1")
    int markAsReadById(Long id);

    List<Notification> findAllByUserId(Long userId);

    List<Notification> findAllByRequestId(Long requestId);
}
