package com.taskmanager.dto;

import com.taskmanager.model.RoleType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponseDTO {

    private String username;
    private RoleType role;
    private String token;
}
