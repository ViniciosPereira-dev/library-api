package com.vinicios.library.mappers;

import com.vinicios.library.dtos.UserCreateDTO;
import com.vinicios.library.dtos.UserResponseDTO;
import com.vinicios.library.entities.User;

import java.util.List;

public class UserMapper {

    public static User toEntity(UserCreateDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public static UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static List<UserResponseDTO> toResponseList(List<User> users) {
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }
}