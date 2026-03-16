package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.AuthResponseDto;
import com.edusmart.edusmart.dto.LoginDto;
import com.edusmart.edusmart.dto.SignupDto;
import com.edusmart.edusmart.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDto request) {
        boolean isSuccess = authService.registerUser(request);

        if (isSuccess) {
            return ResponseEntity.ok().body("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
        AuthResponseDto response = authService.loginUser(request);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email, password, or role combination.");
        }
    }
}