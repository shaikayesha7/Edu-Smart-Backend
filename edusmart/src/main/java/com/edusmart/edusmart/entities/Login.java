package com.edusmart.edusmart.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data // Lombok generates Getters, Setters, and Constructors automatically
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // Standard String instead of Enum

    private String name;
    private String contact;

    @Column(nullable = false)
    private String status = "ACTIVE"; // Default String value
}