package com.logistics.userservice.application.port.output;

import com.logistics.userservice.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User save(User user);
    void deleteUser(User user);
    List<User> getAllUsers();
}
