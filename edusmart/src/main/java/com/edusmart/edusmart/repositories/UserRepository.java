package com.edusmart.edusmart.repositories;

import com.edusmart.edusmart.entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByEmail(String email);

    boolean existsByEmail(String email);
}