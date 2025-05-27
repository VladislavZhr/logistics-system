package com.logistics.userservice.infrastructure.adapter.out.persistence;

import com.logistics.userservice.domain.model.User;
import com.logistics.userservice.application.port.output.UserRepositoryPort;
import com.logistics.userservice.infrastructure.adapter.out.persistence.mapper.UserMapper;
import com.logistics.userservice.infrastructure.adapter.out.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return UserMapper.toDomain(
                jpaRepository.save(UserMapper.toEntity(user))
        );
    }

    @Override
    public void deleteUser(User user) {
        jpaRepository.delete(UserMapper.toEntity(user));
    }

    @Override
    public List<User> getAllUsers(){
        return jpaRepository.findAll().stream().map(UserMapper::toDomain).toList();
    }
}
