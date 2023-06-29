package com.project.security;

import com.project.model.LikedCart;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.service.LikedCartService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final LikedCartService likedCartService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oauth2UserRequest)
            throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(oauth2UserRequest);
        try {
            return processOAuth2User(oauth2User);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2User oauth2User) {
        GoogleOAuth2UserInfo oauth2UserInfo = new GoogleOAuth2UserInfo(oauth2User.getAttributes());
        if (!StringUtils.hasText(oauth2UserInfo.getEmail())) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository
                .findByEmailFetchRoles(oauth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user = updateExistingUser(user, oauth2UserInfo);
        } else {
            user = registerNewUser(oauth2UserInfo);
        }
        return UserPrincipal.create(user, oauth2User.getAttributes());
    }

    private User registerNewUser(GoogleOAuth2UserInfo oauth2UserInfo) {
        User user = new User();
        user.setProvider(User.Provider.GOOGLE);
        user.setRoles(List.of(new Role(Role.RoleName.USER)));
        user.setFirstName(oauth2UserInfo.getFirstName());
        user.setLastName(oauth2UserInfo.getLastName());
        user.setEmail(oauth2UserInfo.getEmail());
        user.setProfileImage(oauth2UserInfo.getImageUrl());
        User savedUser = userRepository.save(user);
        LikedCart likedCart = new LikedCart();
        likedCart.setUser(savedUser);
        likedCartService.save(likedCart);
        return savedUser;
    }

    private User updateExistingUser(User existingUser, GoogleOAuth2UserInfo oauth2UserInfo) {
        existingUser.setFirstName(oauth2UserInfo.getFirstName());
        existingUser.setLastName(oauth2UserInfo.getLastName());
        existingUser.setProfileImage(oauth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
