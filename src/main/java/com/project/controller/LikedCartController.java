package com.project.controller;

import com.project.dto.request.LikedCartRequestDto;
import com.project.dto.response.LikedCartResponseDto;
import com.project.dto.response.ProjectsSearchResponseDto;
import com.project.mapper.LikedCartMapper;
import com.project.mapper.ProjectMapper;
import com.project.model.LikedCart;
import com.project.model.Project;
import com.project.model.User;
import com.project.service.LikedCartService;
import com.project.service.ProjectService;
import com.project.service.UserService;
import com.project.util.PageRequestUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/liked-carts")
public class LikedCartController {
    private final UserService userService;
    private final LikedCartService likedCartService;
    private final LikedCartMapper likedCartMapper;
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;
    private final PageRequestUtil pageRequestUtil;

    @GetMapping
    public List<LikedCartResponseDto> findAll(@RequestParam(defaultValue = "20") Integer count,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pageRequestUtil
                .getPageRequest(count, page, sortBy, "id");
        return likedCartService.findAll(pageRequest).stream()
                .map(likedCartMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private LikedCartResponseDto getById(@PathVariable Long id) {
        return likedCartMapper.modelToDto(likedCartService.getById(id));
    }

    @GetMapping("/by-user/projects")
    public ProjectsSearchResponseDto getProjectsByUser(
            Authentication auth,
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pageRequestUtil
                .getPageRequest(count, page, sortBy, "id");
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        User user = userService.findByEmail(email);
        Page<Project> projectsByUserId = likedCartService
                .findProjectByUserId(user.getId(), pageRequest);
        return new ProjectsSearchResponseDto(likedCartService
                .findProjectByUserId(user.getId(),
                        pageRequest).getContent()
                .stream()
                .map(projectMapper::modelToDto)
                .toList(), projectsByUserId.getTotalPages(), projectsByUserId.getTotalElements());
    }

    @PostMapping
    public LikedCartResponseDto save(@RequestBody LikedCartRequestDto likedCartRequestDto) {
        return likedCartMapper.modelToDto(
                likedCartService.save(
                        likedCartMapper.dtoToModel(likedCartRequestDto)));
    }

    @PutMapping("/{id}")
    public LikedCartResponseDto update(
            @PathVariable Long id,
            @RequestBody LikedCartRequestDto likedCartRequestDto) {
        LikedCart likedCart = likedCartMapper.dtoToModel(likedCartRequestDto);
        likedCart.setId(id);
        return likedCartMapper.modelToDto(likedCartService.save(likedCart));
    }

    @PutMapping("/projects/{projectId}")
    public LikedCartResponseDto addProject(Authentication auth,
                                           @PathVariable Long projectId) throws Exception {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return likedCartMapper.modelToDto(
                likedCartService.addProject(projectService.getById(projectId),
                        userService.findByEmail(email)));
    }

    @DeleteMapping("/projects/{projectId}")
    public LikedCartResponseDto deleteProject(Authentication auth,
                                              @PathVariable Long projectId) throws Exception {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return likedCartMapper.modelToDto(
                likedCartService.deleteProject(projectService.getById(projectId),
                        userService.findByEmail(email)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        likedCartService.deleteById(id);
    }
}
