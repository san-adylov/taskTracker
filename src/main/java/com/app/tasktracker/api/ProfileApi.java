package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.UserRequest;
import com.app.tasktracker.dto.response.GlobalSearchResponse;
import com.app.tasktracker.dto.response.ProfileResponse;
import com.app.tasktracker.dto.response.UserResponse;
import com.app.tasktracker.services.impl.ProfileServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Profile Api", description = "Api Profile to management")
public class ProfileApi {

    private final ProfileServiceImpl userService;

    @PutMapping
    @Operation(summary = "Update", description = "User updating profile user")
    public UserResponse updateUserBy(@RequestBody @Valid UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    @PutMapping("/avatar")
    @Operation(summary = "Update image", description = "User updating profile image")
    public UserResponse updateImage(@RequestParam String userRequestImage) {
        return userService.updateImageUserId(userRequestImage);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Profile", description = "Profile user get by id")
    public ProfileResponse getProfileById(@PathVariable Long userId) {
        return userService.getProfileById(userId);
    }


    @GetMapping("/me")
    @Operation(summary = "My profile", description = "Get my profile")
    public ProfileResponse getMyProfile() {
        return userService.getMyProfile();
    }

    @GetMapping("/global-search")
    public GlobalSearchResponse search(@RequestParam String search) {
        return userService.search(search);
    }
}
