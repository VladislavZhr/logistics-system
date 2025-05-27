package com.logistics.userservice.application.port.input;

import com.logistics.userservice.domain.model.Role;
import com.logistics.userservice.domain.model.User;

import java.util.List;

public interface AdminToolsUseCase {
    void deleteUser(String userId);
    void changeRole(String userId, Role role);
    List<User> getAllUsers();
}
