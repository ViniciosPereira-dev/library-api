package com.vinicios.library.services;

import com.vinicios.library.dtos.UserCreateDTO;
import com.vinicios.library.dtos.UserResponseDTO;
import com.vinicios.library.entities.User;
import com.vinicios.library.mappers.UserMapper;
import com.vinicios.library.repositories.UserRepository;
import com.vinicios.library.services.exceptions.ResourceNotFoundException;
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

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return UserMapper.toResponseDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserCreateDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User updated = userRepository.save(user);

        return UserMapper.toResponseDTO(updated);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> { throw new ResourceNotFoundException("Usuário não encontrado"); }
                );
    }

    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}