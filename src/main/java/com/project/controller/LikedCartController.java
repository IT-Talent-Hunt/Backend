package com.project.controller;

import com.project.dto.request.LikedCartRequestDto;
import com.project.dto.response.LikedCartResponseDto;
import com.project.mapper.LikedCartMapper;
import com.project.model.LikedCart;
import com.project.service.LikedCartService;
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
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/liked-carts")
public class LikedCartController {
    private final LikedCartService likedCartService;
    private final LikedCartMapper likedCartMapper;

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

    @PostMapping
    private LikedCartResponseDto save(@RequestBody LikedCartRequestDto likedCartRequestDto) {
        return likedCartMapper.modelToDto(
                likedCartService.save(
                        likedCartMapper.dtoToModel(likedCartRequestDto)));
    }

    @PostMapping("/add-project/{cartId}/{projectId}")
    private LikedCartResponseDto addProject(@PathVariable Long cartId,
                                            @PathVariable Long projectId) {
        return likedCartMapper.modelToDto(
                likedCartService.addProject(cartId, projectId));
    }

    @PutMapping("/{id}")
    public LikedCartResponseDto updateLikedCart(
            @PathVariable Long id, @RequestBody LikedCartRequestDto likedCartRequestDto) {
        LikedCart likedCart = likedCartMapper.dtoToModel(likedCartRequestDto);
        likedCart.setId(id);
        return likedCartMapper.modelToDto(likedCartService.save(likedCart));
    }

    @DeleteMapping("/{id}")
    public void deleteLikedCart(@PathVariable Long id) {
        LikedCart likedCart = likedCartService.getById(id);
        if (likedCart != null) {
            likedCartService.deleteById(id);
        }
    }
}