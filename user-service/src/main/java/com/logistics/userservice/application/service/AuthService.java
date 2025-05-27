package com.logistics.userservice.application.service;

import com.logistics.userservice.application.port.input.PasswordUseCase;
import com.logistics.userservice.application.port.input.UserUseCase;
import com.logistics.userservice.application.port.output.*;
import com.logistics.userservice.domain.model.Role;
import com.logistics.userservice.domain.model.User;
import com.logistics.userservice.application.port.input.AuthUseCase;
import com.logistics.userservice.infrastructure.adapter.in.controller.dto.authDTO.AuthResponse;
import com.logistics.userservice.infrastructure.adapter.out.kafka.event.PasswordResetRequestedEvent;
import com.logistics.userservice.infrastructure.exception.errors.InvalidCredentialsException;
import com.logistics.userservice.infrastructure.exception.errors.ResourceNotFoundException;
import com.logistics.userservice.infrastructure.exception.errors.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AuthService implements AuthUseCase, UserUseCase, PasswordUseCase {

    private final UserRepositoryPort userRepository;
    private final JwtTokenPort jwtService;
    private final PasswordEncoderPort passwordEncoder;
    private final ForgetTokenPort tokenPort;
    private final NotificationEventPort notificationEventPort;
    private final UserRegistrationBalancePort userRegistrationBalancePort;


    @Override
    public AuthResponse register(String username, String password, String email, Role role) {
        ensureUserNotExists(email);

        String hashedPassword = hashPassword(password);

        User user = buildNewUser(username, hashedPassword, email, role);
        User saved = userRepository.save(user);

        userRegistrationBalancePort.sendUserRegisteredEvent(saved.getId().toString());

        return toAuthResponse(saved);
    }

    private User buildNewUser(String username, String hashedPassword, String email, Role role) {
        return User.builder()
                .username(username)
                .email(email)
                .password(hashedPassword)
                .role(role)
                .build();
    }

    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private void ensureUserNotExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }
    }

    @Override
    public AuthResponse login(String email, String password) {
        User user = validUser(email);

        validPassword(password, user.getPassword());

        return toAuthResponse(user);
    }

    private AuthResponse toAuthResponse(User user) {
        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole());
        return AuthResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    private User validUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void validPassword(String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, newPassword)) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }

    private void validEmail(String email) {
        if(email == null) throw new InvalidCredentialsException("Invalid email");
    }

    @Override
    public AuthResponse getCurrentUser(String email) {
        User user = validUser(email);

        return toAuthResponseWithoutToken(user);
    }

    private AuthResponse toAuthResponseWithoutToken(User user) {
        return AuthResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = validUser(email);

        validPassword(oldPassword, user.getPassword());

        user.setPassword(hashPassword(newPassword));
        userRepository.save(user);
    }

    @Override
    public void forgetPassword(String email) {
        validUser(email);

        String token = tokenPort.generateToken(email);

        PasswordResetRequestedEvent event = new PasswordResetRequestedEvent(email, token);
        notificationEventPort.sendRestEvent(event.getToken(), event.getEmail());
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        String email = tokenPort.getEmailByToken(token);
        validEmail(email);
        User user = validUser(email);
        user.setPassword(hashPassword(newPassword));
        userRepository.save(user);
        tokenPort.deleteToken(token); // видаляємо токен після використання
    }
}
