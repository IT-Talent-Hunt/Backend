package com.project.controller;

import com.project.dto.request.RequestModelRequestDto;
import com.project.dto.response.RequestResponseDto;
import com.project.mapper.RequestMapper;
import com.project.model.Request;
import com.project.service.RequestService;
import com.project.util.PageRequestUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping
    public RequestResponseDto save(@RequestBody RequestModelRequestDto requestDto) {
        return requestMapper.modelToDto(
                requestService.save(
                        requestMapper.dtoToModel(requestDto)));
    }

    @GetMapping("/by-user/{userId}")
    public List<RequestResponseDto> findAllByUserId(@PathVariable Long userId) {
        return requestService.findByUserId(userId)
                .stream()
                .map(requestMapper::modelToDto)
                .toList();
    }

    @GetMapping("/by-project/{projectId}")
    public List<RequestResponseDto> findAllByProjectId(@PathVariable Long projectId) {
        return requestService.findAllByProjectId(projectId)
                .stream()
                .map(requestMapper::modelToDto)
                .toList();
    }

    @GetMapping("/by-projects-owner/{ownerId}")
    public List<RequestResponseDto> findAllByProjectsOwnerId(@PathVariable Long ownerId) {
        return requestService.findAllByProjectsOwnerId(ownerId)
                .stream()
                .map(requestMapper::modelToDto)
                .toList();
    }

    @GetMapping
    public List<RequestResponseDto> findAll(@RequestParam(defaultValue = "20") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pageRequestUtil
                .getPageRequest(count, page, sortBy, "id", "status");
        return requestService.findAll(pageRequest)
                .stream()
                .map(requestMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private RequestResponseDto findById(@PathVariable Long id) {
        return requestMapper.modelToDto(requestService.findById(id));
    }

    @PutMapping("/{id}")
    public RequestResponseDto update(@PathVariable Long id,
                                     @RequestBody RequestModelRequestDto requestDto) {
        Request request = requestMapper.dtoToModel(requestDto);
        request.setId(id);
        return requestMapper.modelToDto(requestService.save(request));
    }

    @PatchMapping("/{id}/{status}")
    public RequestResponseDto updateStatusById(@PathVariable Long id,
                                               @PathVariable String status,
                                               Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String email = details.getUsername();
        requestService.updateStatusById(id, status, email);
        return requestMapper.modelToDto(requestService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id,
                           Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String email = details.getUsername();
        requestService.deleteById(id, email);
    }
}
