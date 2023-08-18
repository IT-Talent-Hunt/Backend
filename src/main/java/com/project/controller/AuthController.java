package com.project.controller;

import com.project.dto.request.LoginDto;
import com.project.dto.request.SignUpDto;
import com.project.dto.response.UserResponseDto;
import com.project.exception.EmailAlreadyRegisteredException;
import com.project.jwt.JwtTokenProvider;
import com.project.mapper.UserMapper;
import com.project.model.User;
import com.project.service.UserService;
import java.util.Map;
import javax.mail.AuthenticationFailedException;
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
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signIn")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findByEmail(loginDto.getEmail());
            String token = jwtTokenProvider.createToken(user.getEmail(),
                    user.getRoles()
                            .stream()
                            .map(a -> a.getRoleName().name())
                            .toList());
            return new ResponseEntity<>(Map.of("token", token, "userResponseDto", userMapper
                    .toDto(user)), HttpStatus.OK);
        } catch (AuthenticationException e) {
            try {
                throw new AuthenticationFailedException("Invalid email or password!" + e);
            } catch (AuthenticationFailedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpDto signUpDto)
            throws EmailAlreadyRegisteredException, javax.naming.AuthenticationException {
        UserResponseDto userResponseDto = userMapper.toDto(userService.registerNewUser(signUpDto));
        String token = jwtTokenProvider.createToken(userResponseDto.email(),
                userResponseDto.roles()
                        .stream()
                        .map(a -> a.getRoleName().name())
                        .toList());
        return new ResponseEntity<>(Map.of("token", token,
                "userResponseDto", userResponseDto), HttpStatus.OK);
    }
}
