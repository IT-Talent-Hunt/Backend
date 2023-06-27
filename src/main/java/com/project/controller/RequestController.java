package com.project.controller;

import com.project.dto.request.RequestModelRequestDto;
import com.project.dto.response.RequestResponseDto;
import com.project.mapper.RequestMapper;
import com.project.model.Request;
import com.project.service.RequestService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class RequestController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @GetMapping
    public List<RequestResponseDto> getAll() {
        return requestService.getAll().stream()
                .map(requestMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private RequestResponseDto getRequestById(@PathVariable Long id) {
        return requestMapper.modelToDto(requestService.getById(id));
    }

    @PostMapping
    public RequestResponseDto save(@RequestBody RequestModelRequestDto requestDto) {
        return requestMapper.modelToDto(
                requestService.save(
                        requestMapper.dtoToModel(requestDto)));
    }

    @PostMapping("/change-status/{id}")
    public RequestResponseDto changeStatus(@PathVariable Long id,
                                           @RequestParam String status) {
        return requestMapper.modelToDto(
                requestService.changeStatus(
                        requestService.getById(id), status));
    }

    @PutMapping("/{id}")
    public RequestResponseDto updateRequest(@PathVariable Long id,
                                      @RequestBody RequestModelRequestDto requestDto) {
        Request request = requestMapper.dtoToModel(requestDto);
        request.setId(id);
        return requestMapper.modelToDto(requestService.save(request));
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable Long id) {
        Request request = requestService.getById(id);
        if (request != null) {
            requestService.deleteById(id);
        }
    }
}
