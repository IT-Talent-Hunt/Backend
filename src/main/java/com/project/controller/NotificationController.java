package com.project.controller;

import com.project.dto.response.NotificationResponseDto;
import com.project.mapper.NotificationMapper;
import com.project.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @GetMapping("/{userId}")
    public List<NotificationResponseDto> findAllByUserId(@PathVariable Long userId) {
        return notificationService.findAllByUserId(userId)
                .stream()
                .map(notificationMapper::modelToDto)
                .toList();
    }

    @PatchMapping("/{id}/mark-as-read")
    public boolean markAsReadById(@PathVariable Long id) {
        return notificationService.markAsReadById(id);
    }
}
