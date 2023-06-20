package com.project.controller;

import com.project.exception.EmailAlreadyRegisteredException;
import com.project.mapper.UserMapper;
import com.project.model.dto.request.LoginDto;
import com.project.model.dto.request.SignUpDto;
import com.project.model.dto.response.UserResponseDto;
import com.project.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signIn")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Authentication successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/signUp")
    public UserResponseDto registerUser(@Valid @RequestBody SignUpDto signUpDto)
            throws EmailAlreadyRegisteredException, javax.naming.AuthenticationException {
        return userMapper.toDto(userService.registerNewUser(signUpDto));
    }
}
