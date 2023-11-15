package com.app.tasktracker.api;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.ResetPasswordRequest;
import com.app.tasktracker.dto.request.SignInRequest;
import com.app.tasktracker.dto.request.SignUpRequest;
import com.app.tasktracker.dto.response.AuthenticationResponse;
import com.app.tasktracker.dto.response.ResetPasswordResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Authentication Api", description = "API - Handles user authentication and access control")
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    @Operation(summary = "SignUp", description = "Register new users")
    public AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    @Operation(summary = "SignIn", description = "Only registered users can login")
    public AuthenticationResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password", description = "Enables password recovery for forgotten accounts via email verification")
    public SimpleResponse forgotPassword(@RequestParam String email, @RequestParam String link) throws MessagingException {
        return authenticationService.forgotPassword(email, link);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Here you can reset your password")
    public ResetPasswordResponse resetPassword(@RequestBody @Valid ResetPasswordRequest passwordRequest) {
        return authenticationService.resetPassword(passwordRequest);
    }

    @PostMapping("/google")
    @Operation(summary = "Google authentication", description = "All users can login with Google")
    public AuthenticationResponse authWithGoogleAccount(@RequestParam String tokenId) throws FirebaseAuthException {
        return authenticationService.authWithGoogle(tokenId);
    }
}