package com.edusmart.edusmart.dto;

import lombok.Data;

@Data
public class SignupDto {
    private String email;
    private String password;
    private String role;
    private String name;
}