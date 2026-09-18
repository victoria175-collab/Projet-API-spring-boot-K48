package com.pkfrc.utilisateurs.service;

import com.pkfrc.utilisateurs.dto.UserResponseDTO;
import com.pkfrc.utilisateurs.dto.UserUpdateRequestDTO;
import com.pkfrc.utilisateurs.entity.User;
import com.pkfrc.utilisateurs.exception.EmailAlreadyExistsException;
import com.pkfrc.utilisateurs.exception.ResourceNotFoundException;
import com.pkfrc.utilisateurs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    public UserResponseDTO update(Long id, UserUpdateRequestDTO request) {
        User user = getOrThrow(id);

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Un utilisateur avec cet email existe déjà");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return toResponse(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = getOrThrow(id);
        userRepository.delete(user);
    }

    private User getOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable avec l'id : " + id));
    }

    private UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
