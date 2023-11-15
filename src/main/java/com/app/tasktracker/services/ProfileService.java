package com.app.tasktracker.services;

import com.app.tasktracker.dto.request.UserRequest;
import com.app.tasktracker.dto.response.GlobalSearchResponse;
import com.app.tasktracker.dto.response.ProfileResponse;
import com.app.tasktracker.dto.response.UserResponse;


public interface ProfileService {
    UserResponse  updateUser(UserRequest userRequest);

     UserResponse updateImageUserId( String image);

    ProfileResponse getProfileById(Long userId);

    ProfileResponse getMyProfile();

    GlobalSearchResponse search(String search);

}