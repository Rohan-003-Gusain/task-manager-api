package com.taskmanager.servie;

import com.taskmanager.dto.AuthResponseDTO;
import com.taskmanager.dto.LoginRequestDTO;
import com.taskmanager.dto.RegisterRequestDTO;

public interface AuthService {

	AuthResponseDTO  register(RegisterRequestDTO dto);
	AuthResponseDTO login(LoginRequestDTO dto);
	
}
