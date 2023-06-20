package com.project.service;

import com.project.model.Request;
import java.util.List;

public interface RequestService {
    Request getById(Long id);

    List<Request> getAll();

    Request save(Request request);

    void deleteById(Long id);

}
