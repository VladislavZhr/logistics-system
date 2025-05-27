package com.logistics.userservice.application.port.output;

public interface ForgetTokenPort {
    String generateToken(String email);
    String getEmailByToken(String token);
    void deleteToken(String token);
}
