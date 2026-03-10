package com.taskmanager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.dto.UserDTO;
import com.taskmanager.servie.AdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController 
@RequestMapping("/api/v1/admin")
public class AdminController {

	private final AdminService adminServices;
	
	@GetMapping("/users")
	public List<UserDTO> getAllUsers(){

		return adminServices.getAllUsers();

    }
	
	@GetMapping("/user/{id}/tasks")
	public List<TaskDTO> getTaskByUserId(@PathVariable Long id) {
		return adminServices.getTaskByUserId(id);
	}

}
