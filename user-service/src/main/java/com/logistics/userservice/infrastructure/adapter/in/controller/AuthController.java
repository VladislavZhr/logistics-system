package com.logistics.userservice.infrastructure.adapter.in.controller;

import com.logistics.userservice.application.port.input.AuthUseCase;
import com.logistics.userservice.application.port.input.PasswordUseCase;
import com.logistics.userservice.application.port.input.UserUseCase;
import com.logistics.userservice.infrastructure.adapter.in.controller.dto.authDTO.AuthResponse;
import com.logistics.userservice.infrastructure.adapter.in.controller.dto.authDTO.LoginRequest;
import com.logistics.userservice.infrastructure.adapter.in.controller.dto.authDTO.RegisterRequest;
import com.logistics.userservice.infrastructure.adapter.in.controller.dto.passwordDTO.ChangePasswordRequest;
import com.logistics.userservice.infrastructure.adapter.in.controller.dto.passwordDTO.ForgotPasswordRequest;
import com.logistics.userservice.infrastructure.adapter.in.controller.dto.passwordDTO.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;
    private final UserUseCase userUseCase;
    private final PasswordUseCase passwordUseCase;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authUseCase.register(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getRole())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authUseCase.login(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(@RequestParam String email) {
        AuthResponse user = userUseCase.getCurrentUser(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        String email = request.getEmail();
        passwordUseCase.changePassword(email, request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully!");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        passwordUseCase.forgetPassword(request.getEmail());
        return ResponseEntity.ok("Password reset link sent to email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordUseCase.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }
}
