package com.project.service;

import com.project.dto.request.SignUpDto;
import com.project.exception.EmailAlreadyRegisteredException;
import com.project.model.User;
import java.util.List;
import javax.naming.AuthenticationException;

public interface UserService {
    User save(User user);

    User findById(Long id);

    List<User> findAllByIdIn(List<Long> userIds);

    User registerNewUser(SignUpDto signUpDto)
            throws EmailAlreadyRegisteredException, AuthenticationException;
}
