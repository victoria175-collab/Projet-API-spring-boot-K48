package com.pkfrc.utilisateurs.service;

import com.pkfrc.utilisateurs.dto.UserResponseDTO;
import com.pkfrc.utilisateurs.dto.UserUpdateRequestDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> findAll();
    UserResponseDTO findById(Long id);
    UserResponseDTO update(Long id, UserUpdateRequestDTO request);
    void delete(Long id);
}
