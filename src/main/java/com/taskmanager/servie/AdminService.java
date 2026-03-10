package com.taskmanager.servie;

import java.util.List;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.dto.UserDTO;

public interface AdminService {
	
	List<UserDTO> getAllUsers();
	List<TaskDTO> getTaskByUserId(Long userId);
}
