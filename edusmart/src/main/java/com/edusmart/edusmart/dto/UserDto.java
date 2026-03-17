package com.edusmart.edusmart.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String role;   // ADMIN, INSTRUCTOR, STUDENT
    private String status; // ACTIVE, INACTIVE, SUSPENDED
    private String password; // Only used when creating a new user from the UI
}
