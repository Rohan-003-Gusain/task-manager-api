package com.taskmanager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.servie.TaskService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

	private final TaskService taskService;
	
	@GetMapping("/all")
	public List<TaskDTO> getAllTasks(Authentication auth) {

		String username = auth.getName();

	    return taskService.getTasksByUser(username);
	}
	
	@GetMapping("/{id}")
	public TaskDTO getTaskById(@PathVariable Long id, Authentication auth) {

	    String username = auth.getName();

	    return taskService.getTaskByIdForUser(id, username);
	}
	
	@PostMapping
	public TaskDTO createTaskById(@RequestBody TaskDTO dto, Authentication auth) {
		
		String username = auth.getName();

	    return taskService.createTask(dto, username);
	}
	
	@PutMapping("{id}")
	public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO dto) {
		
		return taskService.updateTask(id, dto);
	}
	
	@DeleteMapping("{id}") 
	public ResponseEntity<String> deleteTask(@PathVariable Long id) {
		
		taskService.deleteTask(id);
	    
		return ResponseEntity.ok("{\"message\": \"Task deleted successfully\"}");
	}
	
}
