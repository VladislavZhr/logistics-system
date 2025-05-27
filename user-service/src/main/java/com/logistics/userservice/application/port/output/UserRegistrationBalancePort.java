package com.logistics.userservice.application.port.output;

public interface UserRegistrationBalancePort {
    void sendUserRegisteredEvent(String userId);
}
