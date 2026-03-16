package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.AuthResponseDto;
import com.edusmart.edusmart.dto.LoginDto;
import com.edusmart.edusmart.dto.SignupDto;
import com.edusmart.edusmart.entities.Login;
import com.edusmart.edusmart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(SignupDto request) {
        // 1. Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return false;
        }

        // 2. Create new user
        Login user = new Login();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save role as an Uppercase String
        user.setRole(request.getRole() != null ? request.getRole().toUpperCase() : "STUDENT");

        user.setName(request.getName() != null ? request.getName() : "New User");
        user.setStatus("ACTIVE");

        userRepository.save(user);
        return true;
    }
    public AuthResponseDto loginUser(LoginDto request) {
        Optional<Login> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            Login user = optionalUser.get();

            // ⚠️ CHANGED: Verify password ONLY. Removed the role check!
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

                return new AuthResponseDto(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getRole(),
                        user.getStatus()
                );
            }
        }
        return null; // Login failed (wrong email or password)
    }
}