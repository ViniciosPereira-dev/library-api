package com.vinicios.library.services;

import com.vinicios.library.dtos.UserCreateDTO;
import com.vinicios.library.dtos.UserResponseDTO;
import com.vinicios.library.entities.User;
import com.vinicios.library.mappers.UserMapper;
import com.vinicios.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO createUser(UserCreateDTO dto) {
        User user = UserMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return UserMapper.toResponseDTO(saved);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponseDTO);
    }

    public Optional<UserResponseDTO> updateUser(Long id, UserCreateDTO dto) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(dto.getName());
            existingUser.setEmail(dto.getEmail());
            User updated = userRepository.save(existingUser);
            return UserMapper.toResponseDTO(updated);
        });
    }

    public void deleteUser(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> { throw new RuntimeException("Usuário não encontrado"); }
                );
    }

    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}