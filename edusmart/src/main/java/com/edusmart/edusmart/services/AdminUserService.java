package com.edusmart.edusmart.services;


import com.edusmart.edusmart.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getAllUsers();
    UserDto saveOrUpdateUser(UserDto userDto, String adminUsername);
    void deleteUser(Long userId, String adminUsername);
    void updateUserStatus(Long userId, String newStatus, String adminUsername);
}