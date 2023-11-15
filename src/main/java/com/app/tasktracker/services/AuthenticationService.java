package com.app.tasktracker.services;

import com.google.firebase.auth.FirebaseAuthException;
import jakarta.mail.MessagingException;
import com.app.tasktracker.dto.request.ResetPasswordRequest;
import com.app.tasktracker.dto.request.SignInRequest;
import com.app.tasktracker.dto.request.SignUpRequest;
import com.app.tasktracker.dto.response.AuthenticationResponse;
import com.app.tasktracker.dto.response.ResetPasswordResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

public interface AuthenticationService {

    AuthenticationResponse signUp(SignUpRequest signUpRequest);

    AuthenticationResponse signIn(SignInRequest signInRequest);

    ResetPasswordResponse resetPassword(ResetPasswordRequest passwordRequest);

    SimpleResponse forgotPassword(String email, String link) throws MessagingException;

    AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;

}