package com.project.controller;

import com.project.dto.request.UserPartRequestDto;
import com.project.dto.request.UserRequestDto;
import com.project.dto.response.UserResponseDto;
import com.project.mapper.UserMapper;
import com.project.model.User;
import com.project.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id,
                                  @Valid @RequestBody UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        user.setId(id);
        return userMapper.toDto(userService.update(user));
    }

    @PatchMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id,
                                      @Valid @RequestBody UserPartRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        user.setId(id);
        return userMapper.toDto(userService.updatePartial(user));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
