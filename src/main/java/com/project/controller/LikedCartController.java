package com.project.controller;

import com.project.dto.request.LikedCartRequestDto;
import com.project.dto.response.LikedCartResponseDto;
import com.project.mapper.LikedCartMapper;
import com.project.model.LikedCart;
import com.project.model.User;
import com.project.service.LikedCartService;
import com.project.service.ProjectService;
import com.project.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/liked-carts")
public class LikedCartController {
    private final UserService userService;
    private final LikedCartService likedCartService;
    private final LikedCartMapper likedCartMapper;
    private final ProjectService projectService;

    @GetMapping
    public List<LikedCartResponseDto> getAll() {
        return likedCartService.getAll().stream()
                .map(likedCartMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private LikedCartResponseDto getLikedCartById(@PathVariable Long id) {
        return likedCartMapper.modelToDto(likedCartService.getById(id));
    }

    @GetMapping("/by-user")
    public LikedCartResponseDto getByUser(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        User user = userService.findByEmail(email);
        return likedCartMapper.modelToDto(likedCartService.getByUserId(user.getId()));
    }

    @PostMapping
    public LikedCartResponseDto save(@RequestBody LikedCartRequestDto likedCartRequestDto) {
        return likedCartMapper.modelToDto(
                likedCartService.save(
                        likedCartMapper.dtoToModel(likedCartRequestDto)));
    }

    @PutMapping("/{id}")
    public LikedCartResponseDto updateLikedCart(
            @PathVariable Long id,
            @RequestBody LikedCartRequestDto likedCartRequestDto) {
        LikedCart likedCart = likedCartMapper.dtoToModel(likedCartRequestDto);
        likedCart.setId(id);
        return likedCartMapper.modelToDto(likedCartService.save(likedCart));
    }

    @PatchMapping("/project/add")
    public LikedCartResponseDto addProject(Authentication auth,
                                           @RequestParam Long projectId) throws Exception {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return likedCartMapper.modelToDto(
                likedCartService.addProject(projectService.getById(projectId),
                userService.findByEmail(email)));
    }

    @PatchMapping("/project/delete")
    public LikedCartResponseDto deleteProject(Authentication auth,
                                              @RequestParam Long projectId) throws Exception {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return likedCartMapper.modelToDto(
                likedCartService.deleteProject(projectService.getById(projectId),
                userService.findByEmail(email)));
    }

    @DeleteMapping("/{id}")
    public LikedCartResponseDto deleteLikedCart(@PathVariable Long id) {
        LikedCart likedCart = likedCartService.getById(id);
        if (likedCart != null) {
            likedCartService.deleteById(id);
            return likedCartMapper.modelToDto(likedCart);
        }
        return null;
    }
}
