package com.project.controller;

import com.project.dto.request.LoginDto;
import com.project.dto.request.SignUpDto;
import com.project.dto.response.UserResponseDto;
import com.project.exception.EmailAlreadyRegisteredException;
import com.project.mapper.UserMapper;
import com.project.service.UserService;
import javax.mail.AuthenticationFailedException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:3001")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signIn")
    public UserResponseDto authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return userMapper.toDto(userService.findByEmail(loginDto.getEmail()));
        } catch (AuthenticationException e) {
            try {
                throw new AuthenticationFailedException("Invalid email or password!" + e);
            } catch (AuthenticationFailedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @PostMapping("/signUp")
    public UserResponseDto registerUser(@Valid @RequestBody SignUpDto signUpDto)
            throws EmailAlreadyRegisteredException, javax.naming.AuthenticationException {
        return userMapper.toDto(userService.registerNewUser(signUpDto));
    }
}
