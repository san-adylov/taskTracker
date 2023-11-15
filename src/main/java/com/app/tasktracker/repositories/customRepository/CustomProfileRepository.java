package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.request.UserRequest;
import com.app.tasktracker.dto.response.GlobalSearchResponse;
import com.app.tasktracker.dto.response.ProfileResponse;
import com.app.tasktracker.dto.response.UserResponse;


public interface CustomProfileRepository {

    UserResponse updateUser(UserRequest userRequest);

    ProfileResponse getProfileById(Long userId);

    ProfileResponse getMyProfile();

    GlobalSearchResponse search(String search);
}
