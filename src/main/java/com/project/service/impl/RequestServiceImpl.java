package com.project.service.impl;

import com.project.exception.DuplicateRequestException;
import com.project.exception.ProjectOwnershipException;
import com.project.exception.RequestOwnershipException;
import com.project.mapper.NotificationMapper;
import com.project.model.Notification;
import com.project.model.Project;
import com.project.model.Request;
import com.project.model.User;
import com.project.repository.RequestRepository;
import com.project.service.NotificationService;
import com.project.service.RequestService;
import com.project.service.TeamService;
import com.project.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final TeamService teamService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationMapper notificationMapper;

    @Override
    public Request save(Request request) {
        Project project = request.getProject();
        String email = project.getOwner().getEmail();
        validateRequestDoesNotExist(project, request.getUser());
        Notification notification = Notification.builder()
                .user(project.getOwner())
                .request(request)
                .message(request.getUser().getFirstName()
                        + " " + request.getUser().getLastName()
                        + " want to apply "
                        + project.getName()
                        + " project.")
                .build();
        Request savedRequest = requestRepository.save(request);
        notificationService.save(notification);
        sendNotificationEmail(email, notification);
        return savedRequest;
    }

    @Override
    public Request update(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request findById(Long id) {
        return requestRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Couldn't find request by id: " + id));
    }

    @Override
    public List<Request> findAll(PageRequest pageRequest) {
        return requestRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Request> findByUserId(Long userId) {
        return requestRepository.findByUserId(userId);
    }

    @Override
    public List<Request> findAllByProjectId(Long projectId) {
        return requestRepository.findAllByProjectId(projectId);
    }

    @Override
    public List<Request> findAllByProjectsOwnerId(Long ownerId) {
        return requestRepository.findAllByProjectsOwnerId(ownerId);
    }

    @Override
    public void updateStatusById(Long id, String status, String email) {
        Request request = findById(id);
        User projectOwner = userService.findByEmail(email);
        User requestOwner = request.getUser();
        validateProjectOwnership(request, projectOwner);
        if (Request.Status.valueOf(status).equals(Request.Status.ACCEPTED)) {
            handleAcceptedRequest(request, requestOwner);
        } else if (Request.Status.valueOf(status).equals(Request.Status.REJECTED)) {
            handleRejectedRequest(request, requestOwner);
        }
        requestRepository.updateStatusById(id, Request.Status.valueOf(status));
    }

    @Override
    public void deleteById(Long id, String email) {
        User user = userService.findByEmail(email);
        Request request = findById(id);
        if (!user.equals(request.getUser())) {
            throw new RequestOwnershipException("You are not the owner of this"
                    + " request and cannot delete it");
        }
        requestRepository.deleteById(id);
    }

    private void validateRequestDoesNotExist(Project project, User requester) {
        if (requestRepository.existsByProjectAndUserAndStatus(
                project, requester, Request.Status.PENDING)) {
            throw new DuplicateRequestException("You have already applied to this project.");
        }
    }

    private void validateProjectOwnership(Request request, User user) {
        if (!user.equals(request.getProject().getOwner())) {
            throw new ProjectOwnershipException("You are not the owner of this project "
                    + "and cannot update its status");
        }
    }

    private void handleAcceptedRequest(Request request, User user) {
        teamService.addUser(request.getProject().getId(), user.getId());
        Notification notification = Notification.builder()
                .user(request.getUser())
                .request(request)
                .message("Your application to join the " + request.getProject().getName()
                        + " has been approved. Welcome to the team!")
                .build();
        notificationService.save(notification);
        sendNotificationEmail(user.getEmail(), notification);
    }

    private void handleRejectedRequest(Request request, User user) {
        Notification notification = Notification.builder()
                .user(request.getUser())
                .request(request)
                .message("Your application to join the project " + request.getProject().getName()
                        + " has been rejected.")
                .build();
        notificationService.save(notification);
        sendNotificationEmail(user.getEmail(), notification);
    }

    private void sendNotificationEmail(String email, Notification notification) {
        simpMessagingTemplate.convertAndSendToUser(
                email, "/queue/notification", notificationMapper.modelToDto(notification));
    }
}
