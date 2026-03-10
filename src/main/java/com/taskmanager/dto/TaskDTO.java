package com.taskmanager.dto;

import lombok.Data;

@Data
public class TaskDTO {

	private Long taskId;
	private String title;
	private String description;
	private String status;
	private Long userId;
}
