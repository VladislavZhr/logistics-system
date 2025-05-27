package com.logistics.userservice.application.port.input;

public interface PasswordUseCase {
    void changePassword(String email, String oldPassword, String newPassword);
    void forgetPassword(String email);
    void resetPassword(String token, String newPassword);
}
