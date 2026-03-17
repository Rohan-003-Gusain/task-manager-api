package com.taskmanager.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.mapper.TaskMapper;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.servie.TaskService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskServiceIMPL implements TaskService{

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	
	private final TaskMapper taskMapper;

	@Override
	public TaskDTO createTask(TaskRequestDTO  dto, String username) {

		User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = taskMapper.toEntity(dto);
        task.setUser(user);

        Task saved = taskRepository.save(task);

        return taskMapper.toDTO(saved);
	}

	@Override
	public List<TaskDTO> getAllTask() {
		
		return taskRepository.findAll()
				.stream()
				.map(taskMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public TaskDTO updateTask(Long id, TaskRequestDTO  dto) {
		
		Task task = taskRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
		
		task.setTitle(dto.getTitle());
		task.setDescription(dto.getDescription());
		task.setStatus(dto.getStatus());
		
		taskRepository.save(task);
		
		return taskMapper.toDTO(task);
	}

	@Override
	@Transactional
	public void deleteTask(Long id) {

		if (!taskRepository.existsById(id)) {
	        throw new ResourceNotFoundException("Task not found with id " + id);
	    }
		
	    taskRepository.deleteById(id);
	}

	@Override
	public List<TaskDTO> getTasksByUser(String username) {
		
		Long userId = userRepository.findByUsername(username)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found"))
	            .getId();

	    List<Task> tasks = taskRepository.findByUserId(userId);

	    return tasks.stream()
	            .map(taskMapper::toDTO)
	            .toList();
	}

	public TaskDTO getTaskByIdForUser(Long id, String username) {

	    Task task = taskRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Task not found"));

	    if (!task.getUser().getUsername().equals(username)) {
	        throw new RuntimeException("Access denied");
	    }

	    return taskMapper.toDTO(task);
	}

}
