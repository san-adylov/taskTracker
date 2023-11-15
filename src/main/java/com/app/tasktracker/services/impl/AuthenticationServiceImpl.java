package com.app.tasktracker.services.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.request.ResetPasswordRequest;
import com.app.tasktracker.dto.request.SignInRequest;
import com.app.tasktracker.dto.request.SignUpRequest;
import com.app.tasktracker.dto.response.AuthenticationResponse;
import com.app.tasktracker.dto.response.ResetPasswordResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.models.User;
import com.app.tasktracker.enums.Role;
import com.app.tasktracker.exceptions.BadCredentialException;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.repositories.UserRepository;
import com.app.tasktracker.services.AuthenticationService;

import java.io.IOException;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            log.error(String.format("User with email: %s already exist!", signUpRequest.email()));
            throw new NotFoundException(String.format("User with email: %s already exist!", signUpRequest.email()));
        }
        User user = new User();
        user.setFirstName(signUpRequest.firstName());
        user.setLastName(signUpRequest.lastName());
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setImage("Default image");
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        if (signInRequest.email().isBlank()) {
            log.error("User with email: " + signInRequest.email() + " not found");
            throw new BadCredentialException("User with email: " + signInRequest.email() + " not found");
        }
        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() -> {
            log.error("User with email: " + signInRequest.email() + " not found");
            return new NotFoundException("User with email: " + signInRequest.email() + " not found");
        });

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            log.error("Incorrect password !");
            throw new BadCredentialException("Incorrect password !");
        }

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwtToken)
                .build();

    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest repeatPassword) {
        User user = userRepository.findById(repeatPassword.userId()).orElseThrow(
                () -> {
                    log.error("User with id: " + repeatPassword.userId() + " not found!");
                    return new NotFoundException("User with id: " + repeatPassword.userId() + " not found!");
                }
        );
        if (!repeatPassword.newPassword().equals(repeatPassword.repeatPassword())) {
            log.error("Passwords do not match");
            throw new BadCredentialException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(repeatPassword.newPassword()));
        userRepository.save(user);
        log.info("Password updated!");
        String jwt = jwtService.generateToken(user);

        return ResetPasswordResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .jwtToken(jwt)
                .message("Password updated!")
                .build();
    }

    @Override
    public SimpleResponse forgotPassword(String email, String link) throws MessagingException {
        User user = userRepository.getUserByEmail(email).orElseThrow(
                () -> {
                    log.error("User with email:" + email + "not found!");
                    return new NotFoundException("User with email:" + email + "not found!");
                }
        );

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setSubject("Task Tracker");
        helper.setFrom("rusi.studio.kgz@gmail.com");
        helper.setTo(email);
        helper.setText("Hello " + user.getFirstName() + "," +
                "\n" +
                "You have requested to reset your password. Please click the link below to reset your password:\n" +
                "\n" +
                link + "/" + user.getId() +
                "\n" +
                "If you didn't request a password reset, please ignore this email.\n" +
                "\n" +
                "Best regards,\n" +
                "Your App Team");
        javaMailSender.send(mimeMessage);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("email send")
                .build();
    }

    @PostConstruct
    public void initFireBaseForAuthGoogle() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                new ClassPathResource("tasktracker.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();
        FirebaseApp.initializeApp(firebaseOptions);
    }

    @Override
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        User user;
        if (!userRepository.existsByEmail(firebaseToken.getEmail())) {
            if (firebaseToken.getName().matches(" ")) {
                String[] name = firebaseToken.getName().split(" ");
                user = new User();
                user.setFirstName(name[0]);
                user.setLastName(name[1]);
            } else {
                user = new User();
                user.setFirstName(firebaseToken.getName());
            }
            user.setEmail(firebaseToken.getEmail());
            user.setPassword(passwordEncoder.encode(firebaseToken.getEmail()));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }

        user = userRepository.getUserByEmail(firebaseToken.getEmail()).orElseThrow(
                () -> {
                    log.error("User with this email not found!");
                    return new NotFoundException("User with this email not found!");
                }
        );
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

}
