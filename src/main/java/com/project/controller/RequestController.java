package com.project.controller;

import com.project.dto.request.RequestModelRequestDto;
import com.project.dto.response.RequestResponseDto;
import com.project.mapper.RequestMapper;
import com.project.model.Request;
import com.project.service.RequestService;
import com.project.util.PageRequestUtil;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final PageRequestUtil pageRequestUtil;

    @GetMapping
    public List<RequestResponseDto> findAll(@RequestParam(defaultValue = "20") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pageRequestUtil
                .getPageRequest(count, page, sortBy, "id", "status");
        return requestService.findAll(pageRequest).stream()
                .map(requestMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private RequestResponseDto getById(@PathVariable Long id) {
        return requestMapper.modelToDto(requestService.getById(id));
    }

    @PostMapping
    public RequestResponseDto save(@Valid @RequestBody RequestModelRequestDto requestDto) {
        return requestMapper.modelToDto(
                requestService.save(
                        requestMapper.dtoToModel(requestDto)));
    }

    @PatchMapping("/{id}/status")
    public RequestResponseDto changeStatus(@PathVariable Long id,
                                           @RequestParam String status) {
        return requestMapper.modelToDto(
                requestService.changeStatus(
                        requestService.getById(id), status));
    }

    @PutMapping("/{id}")
    public RequestResponseDto update(@PathVariable Long id,
                                      @Valid @RequestBody RequestModelRequestDto requestDto) {
        Request request = requestMapper.dtoToModel(requestDto);
        request.setId(id);
        return requestMapper.modelToDto(requestService.save(request));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        requestService.deleteById(id);
    }
}
