package com.project.config;

import com.project.model.Role;
import com.project.model.User;
import com.project.service.UserService;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer {
    private final UserService userService;

    @PostConstruct
    public void initialize() {
        Role role = new Role();
        role.setRoleName(Role.RoleName.USER);
        User user = new User();
        user.setEmail("user");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("1234");
        userService.save(user);
    }
}
