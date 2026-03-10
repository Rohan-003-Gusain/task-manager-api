package com.taskmanager.dto;

import com.taskmanager.model.RoleType;
import lombok.Data;

@Data
public class UserDTO {

	private Long id;
    private String username;
    private String email;
    private RoleType role;
	
}
