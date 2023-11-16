package com.app.tasktracker.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.request.UserRequest;
import com.app.tasktracker.dto.response.GlobalSearchResponse;
import com.app.tasktracker.dto.response.ProfileResponse;
import com.app.tasktracker.dto.response.UserResponse;
import com.app.tasktracker.models.User;


import com.app.tasktracker.repositories.customRepository.customRepositoryImpl.CustomProfileRepositoryImpl;
import com.app.tasktracker.services.ProfileService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final CustomProfileRepositoryImpl queryJdbc;

    private final JwtService jwtService;

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        return queryJdbc.updateUser(userRequest);
    }

    @Override
    public UserResponse updateImageUserId(String image) {
        User user = jwtService.getAuthentication();
        user.setImage(image);
        log.info("Updated image user");
        return UserResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .avatar(user.getImage())
                .build();
    }

    @Override
    public ProfileResponse getProfileById(Long userId) {
        return queryJdbc.getProfileById(userId);
    }

    @Override
    public ProfileResponse getMyProfile() {
        return queryJdbc.getMyProfile();
    }

    @Override
    public GlobalSearchResponse search(String search) {
        return queryJdbc.search(search);
    }
}
