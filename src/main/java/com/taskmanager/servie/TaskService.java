package com.taskmanager.servie;

import java.util.List;

import com.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.dto.TaskDTO;

public interface TaskService {

	TaskDTO createTask(TaskRequestDTO dto, String username);
	
	List<TaskDTO> getAllTask();
	
	List<TaskDTO> getTasksByUser(String username);
	
	TaskDTO updateTask(Long id, TaskRequestDTO dto);
	
	void deleteTask(Long id);

	TaskDTO getTaskByIdForUser(Long id, String username);
}
