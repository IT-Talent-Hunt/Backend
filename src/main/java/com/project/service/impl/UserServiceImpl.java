package com.project.service.impl;

import com.project.dto.request.SignUpDto;
import com.project.exception.EmailAlreadyRegisteredException;
import com.project.exception.UserNotFoundException;
import com.project.model.LikedCart;
import com.project.model.Role;
import com.project.model.SocialLink;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.service.LikedCartService;
import com.project.service.UserService;
import java.util.ArrayList;
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
    private final LikedCartService likedCartService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setRoles(List.of(new Role(Role.RoleName.USER)));
        setDefaultLinks(user);
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
    public User findByEmail(String email) {
        return userRepository.findByEmailFetchRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Couldn't find user by email " + email));
    }

    @Override
    public List<User> findAllByIdIn(List<Long> userIds) {
        return userRepository.findAllByIdIn(userIds);
    }

    @Override
    public User update(User user) {
        User userFromDb = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(
                        "Couldn't find user by id: " + user.getId()));
        if (user.getPassword() == null) {
            user.setPassword(userFromDb.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(userFromDb.getPassword()));
        }
        if (userFromDb.getRegistrationDate() != null) {
            user.setRegistrationDate(userFromDb.getRegistrationDate());
        }
        user.setProvider(userFromDb.getProvider());
        user.setRoles(userFromDb.getRoles());
        return userRepository.save(user);
    }

    @Override
    public User updatePartial(User user) {
        User userFromDb = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(
                        "Couldn't find user by id: " + user.getId()));
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setSpeciality(user.getSpeciality());
        return userRepository.save(userFromDb);
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
        User savedUser = save(registerUser);
        LikedCart likedCart = new LikedCart();
        likedCart.setUser(savedUser);
        likedCartService.save(likedCart);
        return savedUser;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private void setDefaultLinks(User user) {
        List<SocialLink> socialLinks = new ArrayList<>();
        socialLinks.add(new SocialLink("Email", user.getEmail()));
        socialLinks.add(new SocialLink("Telegram", ""));
        socialLinks.add(new SocialLink("LinkedIn", ""));
        user.setSocialLinks(socialLinks);
    }
}
