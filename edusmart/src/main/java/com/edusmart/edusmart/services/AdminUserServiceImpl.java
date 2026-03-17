package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.UserDto;
import com.edusmart.edusmart.entities.ActivityLog;
import com.edusmart.edusmart.entities.Login;
import com.edusmart.edusmart.repositories.ActivityLogRepository;
import com.edusmart.edusmart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto saveOrUpdateUser(UserDto dto, String adminUsername) {
        Login user;
        boolean isNew = false;

        if (dto.getId() != null) {
            user = userRepository.findById(dto.getId()).orElseThrow();
        } else {
            user = new Login();
            isNew = true;
            // Only set/hash password for brand new users
            String rawPassword = (dto.getPassword() != null && !dto.getPassword().isEmpty()) ? dto.getPassword() : "default123";
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());

        Login savedUser = userRepository.save(user);

        // 📝 LOG THE ACTIVITY
        logActivity(adminUsername, isNew ? "Created a new " + dto.getRole() + " account:" : "Updated account for:", dto.getName(), "ADMIN");

        return mapToDto(savedUser);
    }

    @Override
    public void updateUserStatus(Long userId, String newStatus, String adminUsername) {
        Login user = userRepository.findById(userId).orElseThrow();
        user.setStatus(newStatus);
        userRepository.save(user);

        // 📝 LOG THE ACTIVITY
        logActivity(adminUsername, "Changed account status to " + newStatus + " for:", user.getName(), "ADMIN");
    }

    @Override
    public void deleteUser(Long userId, String adminUsername) {
        Login user = userRepository.findById(userId).orElseThrow();
        userRepository.deleteById(userId);

        // 📝 LOG THE ACTIVITY
        logActivity(adminUsername, "Deleted account for:", user.getName(), "ADMIN");
    }

    // --- Helper Methods ---

    private UserDto mapToDto(Login user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }

    private void logActivity(String username, String action, String detail, String type) {
        ActivityLog log = new ActivityLog();
        log.setUsername(username != null ? username : "Admin");
        log.setAction(action);
        log.setDetail(detail);
        log.setType(type);
        activityLogRepository.save(log);
    }
}
