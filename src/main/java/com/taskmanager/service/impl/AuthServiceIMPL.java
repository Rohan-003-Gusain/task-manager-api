package com.taskmanager.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanager.dto.*;
import com.taskmanager.exception.ConflictException;
import com.taskmanager.exception.UnauthorizedException;
import com.taskmanager.model.RoleType;
import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.security.JwtUtil;
import com.taskmanager.servie.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceIMPL implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

	
	@Override
	public AuthResponseDTO register(RegisterRequestDTO dto) {
		
		if(userRepository.existsByUsername(dto.getUsername())){
            throw new ConflictException("Username already exists");
        }
		
		User user = new User();
		
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(RoleType.USER);
		
		userRepository.save(user);
        
		return AuthResponseDTO.builder()
	            .username(user.getUsername())
	            .role(user.getRole())
	            .token("REGISTER_SUCCESS")
	            .build();
	}
	
	@Override
	public AuthResponseDTO login(LoginRequestDTO dto) {
		
		User user = userRepository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new UnauthorizedException("User not found with username"));
		
		if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new UnauthorizedException("Invalid password");
		}
		
		String token = jwtUtil.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
	}

}
