package com.project.service.impl;

import com.project.model.Request;
import com.project.repository.RequestRepository;
import com.project.service.ProjectService;
import com.project.service.RequestService;
import com.project.service.TeamService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final ProjectService projectService;
    private final TeamService teamService;

    @Override
    public Request getById(Long id) {
        return requestRepository.getById(id);
    }

    @Override
    public List<Request> findAll(PageRequest pageRequest) {
        return requestRepository.findAll(pageRequest).toList();
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
        // TODO
        return null;
    }
}
