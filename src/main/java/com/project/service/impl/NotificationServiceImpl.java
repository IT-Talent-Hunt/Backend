package com.project.service.impl;

import com.project.model.Notification;
import com.project.repository.NotificationRepository;
import com.project.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private static final int READ_NOTIFICATION = 1;
    private final NotificationRepository notificationRepository;

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public boolean markAsReadById(Long id) {
        return READ_NOTIFICATION == notificationRepository.markAsReadById(id);
    }

    @Override
    public List<Notification> findAllByUserId(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }
}
