package com.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.model.Task;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

	Task toEntity(TaskRequestDTO dto);
	
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "id", target = "taskId")
	TaskDTO toDTO(Task task);
}
