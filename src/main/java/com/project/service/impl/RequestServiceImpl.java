package com.project.service.impl;

import com.project.model.ProjectStatus;
import com.project.model.Request;
import com.project.model.RequestStatus;
import com.project.repository.RequestRepository;
import com.project.service.ProjectService;
import com.project.service.RequestService;
import com.project.service.TeamService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final ProjectService projectService;
    private final TeamService teamService;

    public RequestServiceImpl(RequestRepository requestRepository,
                              ProjectService projectService,
                              TeamService teamService) {
        this.requestRepository = requestRepository;
        this.projectService = projectService;
        this.teamService = teamService;
    }

    @Override
    public Request getById(Long id) {
        return requestRepository.getById(id);
    }

    @Override
    public List<Request> getAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public Request changeStatus(Request request, String status) {
        request.setStatus(RequestStatus.Status.valueOf(status));
        if (request.getStatus() == RequestStatus.Status.ACCEPTED) {
            teamService.addUser(request.getProject().getTeam(),
                    request.getUser(), request.getSpecialityName());
            if (request.getProject().getTeam().getSpecialities().size() == 0) {
                projectService.changeStatus(request.getProject(), ProjectStatus.Status.IN_PROGRESS);
            }
        }
        return save(request);
    }
}
