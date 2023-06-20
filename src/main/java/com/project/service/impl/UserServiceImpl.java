package com.project.service.impl;

import com.project.dto.request.SignUpDto;
import com.project.exception.EmailAlreadyRegisteredException;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setRoles(List.of(new Role(Role.RoleName.USER)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Couldn't find user by id: " + id));
    }

    @Override
    public List<User> findAllByIdIn(List<Long> userIds) {
        return userRepository.findAllByIdIn(userIds);
    }

    @Override
    public User registerNewUser(SignUpDto signUpDto) throws EmailAlreadyRegisteredException,
            AuthenticationException {
        Optional<User> byEmailFetchRoles = userRepository
                .findByEmailFetchRoles(signUpDto.getEmail());
        if (byEmailFetchRoles.isPresent()) {
            throw new EmailAlreadyRegisteredException("Email is already registered: "
                    + signUpDto.getEmail());
        }
        if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            throw new AuthenticationException("Passwords are not the same");
        }
        User registerUser = new User();
        registerUser.setEmail(signUpDto.getEmail());
        registerUser.setPassword(signUpDto.getPassword());
        registerUser.setProvider(User.Provider.LOCAL);
        return save(registerUser);
    }
}
