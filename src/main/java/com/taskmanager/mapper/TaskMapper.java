package com.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

	@Mapping(source = "userId", target = "user.id")
	@Mapping(source = "taskId", target = "id")
	Task toEntity(TaskDTO dto);
	
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "id", target = "taskId")
	TaskDTO toDTO(Task task);
}
