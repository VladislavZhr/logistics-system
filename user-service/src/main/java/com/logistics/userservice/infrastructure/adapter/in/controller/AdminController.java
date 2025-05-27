package com.logistics.userservice.infrastructure.adapter.in.controller;

import com.logistics.userservice.application.port.input.AdminToolsUseCase;
import com.logistics.userservice.domain.model.Role;
import com.logistics.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminToolsUseCase adminToolsUseCase;

    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam ("userId") String userId) {
        adminToolsUseCase.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminToolsUseCase.getAllUsers());
    }

    @PostMapping("/change-role")
    public ResponseEntity<String> changeUserRole(@RequestParam ("userId") String userId, Role role){
        adminToolsUseCase.changeRole(userId, role);
        return ResponseEntity.ok("User role changed successfully!");
    }
}
