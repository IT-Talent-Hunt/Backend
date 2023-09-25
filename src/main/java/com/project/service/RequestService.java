package com.project.service;

import com.project.model.Request;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface RequestService {
    Request update(Request request);

    Request findById(Long id);

    List<Request> findAll(PageRequest pageRequest);

    List<Request> findByUserId(Long userId);

    List<Request> findAllByProjectId(Long projectId);

    List<Request> findAllByProjectsOwnerId(Long ownerId);

    void updateStatusById(Long id, String status, String email);

    Request save(Request request);

    void deleteById(Long id, String email);
}
