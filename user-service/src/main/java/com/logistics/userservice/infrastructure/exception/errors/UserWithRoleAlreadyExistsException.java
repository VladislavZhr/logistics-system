package com.logistics.userservice.infrastructure.exception.errors;

public class UserWithRoleAlreadyExistsException extends RuntimeException {
    public UserWithRoleAlreadyExistsException(String message) {
        super(message);
    }
}
