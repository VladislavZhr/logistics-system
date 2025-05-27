package com.logistics.userservice.application.service;

import com.logistics.userservice.application.port.input.AdminToolsUseCase;
import com.logistics.userservice.application.port.output.UserDeletePort;
import com.logistics.userservice.application.port.output.UserRepositoryPort;
import com.logistics.userservice.domain.model.Role;
import com.logistics.userservice.domain.model.User;
import com.logistics.userservice.infrastructure.exception.errors.ResourceNotFoundException;
import com.logistics.userservice.infrastructure.exception.errors.UserWithRoleAlreadyExistsException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminService implements AdminToolsUseCase {

    private final UserRepositoryPort userRepository;
    private  final UserDeletePort userDeletePort;

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteUser(findUser(userId));
        userDeletePort.sendDeleteUser(userId);
    }

    @Override
    public void changeRole(String userId, Role role) {
        User user = findUser(userId);
        idempotentChangeRole(user,role);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    private User findUser(String userId){
        return userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void idempotentChangeRole(User user,  Role role) {
        if(user.getRole().equals(role)) {
            throw new UserWithRoleAlreadyExistsException("User already has role " + role);
        }
    }
}
