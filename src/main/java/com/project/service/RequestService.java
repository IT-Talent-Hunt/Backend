package com.project.service;

import com.project.model.Request;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface RequestService {
    Request getById(Long id);

    List<Request> findAll(PageRequest pageRequest);

    Request save(Request request);

    void deleteById(Long id);

    Request changeStatus(Request request, String status);
}
