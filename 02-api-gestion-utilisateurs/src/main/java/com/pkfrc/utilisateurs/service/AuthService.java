package com.pkfrc.utilisateurs.service;

import com.pkfrc.utilisateurs.dto.AuthResponseDTO;
import com.pkfrc.utilisateurs.dto.LoginRequestDTO;
import com.pkfrc.utilisateurs.dto.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}
