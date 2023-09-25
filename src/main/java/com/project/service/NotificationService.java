package com.project.service;

import com.project.model.Notification;
import java.util.List;

public interface NotificationService {
    Notification save(Notification notification);

    boolean markAsReadById(Long id);

    List<Notification> findAllByUserId(Long userId);
}
