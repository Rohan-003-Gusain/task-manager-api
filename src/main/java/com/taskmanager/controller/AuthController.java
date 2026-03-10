package com.taskmanager.controller;

import org.springframework.web.bind.annotation.*;

import com.taskmanager.dto.AuthResponseDTO;
import com.taskmanager.dto.LoginRequestDTO;
import com.taskmanager.dto.RegisterRequestDTO;
import com.taskmanager.servie.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    // REGISTER
    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO dto) {
    		
    		return authService.register(dto);

    }

    // LOGIN
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO dto) {
    	
    		return authService.login(dto);
    }
        
}