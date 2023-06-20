package com.project.service;

import com.project.exception.EmailAlreadyRegisteredException;
import com.project.model.User;
import com.project.model.dto.request.SignUpDto;
import javax.naming.AuthenticationException;

public interface UserService {
    User save(User user);

    User registerNewUser(SignUpDto signUpDto)
            throws EmailAlreadyRegisteredException, AuthenticationException;
}
