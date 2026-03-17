package com.taskmanager.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.dto.UserDTO;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.mapper.TaskMapper;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.servie.AdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminServiceIMPL implements AdminService {

	private final UserRepository userRepository;
	private final TaskRepository taskRepository;
	private final TaskMapper taskMapper;
	
	@Override
	public List<UserDTO> getAllUsers() {
		
		List<User> users = userRepository.findAll();

	    return users.stream()
	            .map(user -> {
	                UserDTO dto = new UserDTO();
	                dto.setId(user.getId());
	                dto.setUsername(user.getUsername());
	                dto.setEmail(user.getEmail());
	                dto.setRole(user.getRole());
	                return dto;
	            })
	            .toList();
	}
	
	@Override
	public List<TaskDTO> getTaskByUserId(Long userId) {
		
		User user = userRepository.findById(userId)
		        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		List<Task> tasks = taskRepository.findByUserId(user.getId());
		
		return tasks.stream()
		        .map(taskMapper::toDTO)
		        .toList();
	}
}
