//package com.logistics.authservice.application.service;
//
//import com.logistics.authservice.application.port.output.NotificationEventPort;
//import com.logistics.authservice.application.port.output.TokenPort;
//import com.logistics.authservice.application.port.output.UserRepositoryPort;
//import com.logistics.authservice.domain.model.Role;
//import com.logistics.authservice.domain.model.User;
//import com.logistics.authservice.infrastructure.adapter.in.controller.dto.authDTO.AuthResponse;
//import com.logistics.authservice.infrastructure.adapter.in.controller.dto.authDTO.RegisterRequest;
//import com.logistics.authservice.infrastructure.adapter.out.persistence.UserRepositoryAdapter;
//import com.logistics.authservice.infrastructure.exception.errors.UserAlreadyExistsException;
//import com.logistics.authservice.infrastructure.security.JwtService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class AuthServiceTest {
//
//    @Mock
//    private UserRepositoryPort userRepository;
//
//    @Mock
//    private TokenPort tokenPort;
//
//    @Mock
//    private NotificationEventPort notificationEventPort;
//
//    @Mock
//    private JwtService jwtService;
//
//    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    private AuthService authService;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        authService = new AuthService(
//                 userRepository,
//                 jwtService,
//                 passwordEncoder,
//                 tokenPort,
//                 notificationEventPort
//        );
//    }
//
//    @Test
//    void register_shouldCreateUser_whenEmailIsNotTaken(){
//        RegisterRequest request = new RegisterRequest(
//                "john",
//                "john@example.com",
//                "password123",
//                Role.USER
//        );
//
//        when(userRepository.findByEmail(request.getEmail()))
//                .thenReturn(Optional.empty());
//
//        String mockedToken = "mocked-jwt-token";
//        when(jwtService.generateToken(request.getEmail(), request.getRole()))
//                .thenReturn(mockedToken);
//
//        // мок: збереження користувача
//        User savedUser = User.builder()
//                .username(request.getUsername())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword())) // хеш з реального encoder
//                .role(request.getRole())
//                .balance(BigDecimal.ZERO)
//                .build();
//
//        when(userRepository.save(any(User.class))).thenReturn(savedUser);
//
//        AuthResponse response = authService.register(request);
//
//        // then
//        assertEquals(request.getUsername(), response.getUsername());
//        assertEquals(request.getEmail(), response.getEmail());
//        assertEquals(request.getRole(), response.getRole());
//        assertEquals(BigDecimal.ZERO, response.getBalance());
//        assertEquals(mockedToken, response.getToken());
//
//        // перевіряємо що save був викликаний
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//    @Test
//    void register_shouldThrowException_whenUserAlreadyExists() {
//        // given
//        RegisterRequest request = new RegisterRequest(
//                "john@example.com",
//                "password123",
//                "john",
//                Role.USER
//        );
//
//        // Користувач вже існує в системі
//        User existingUser = User.builder()
//                .username("johnny")
//                .email(request.getEmail())
//                .build();
//
//        when(userRepository.findByEmail(request.getEmail()))
//                .thenReturn(Optional.of(existingUser));
//
//        // when + then
//        assertThrows(UserAlreadyExistsException.class, () -> {
//            authService.register(request);
//        });
//
//        // Переконуємось, що save() не викликався
//        verify(userRepository, never()).save(any());
//    }
//
//
//
//}
