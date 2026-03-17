package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.AdminOverviewDto;
import com.edusmart.edusmart.dto.UserDto;
import com.edusmart.edusmart.services.AdminService;
import com.edusmart.edusmart.services.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "http://localhost:4200") // 🚀 Make sure this is here!
public class AdminController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserDto> createOrUpdateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(adminUserService.saveOrUpdateUser(userDto, "System Admin"));
    }
}
