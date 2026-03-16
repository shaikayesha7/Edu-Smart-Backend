package com.edusmart.edusmart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private Long id;
    private String email;
    private String name;
    private String role;
    private String status;
}
