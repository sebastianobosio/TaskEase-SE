package com.mycompany.taskmanager.model;

import java.time.LocalTime;
import java.time.LocalDate;

public class Task {
	public enum TaskStatus {
		COMPLETED,
		ONGOING,
		NOTSTARTED,	
	}
	
	private String title;
	private String description;
	private LocalDate dueDate;
	private LocalTime dueTime;
	private TaskStatus status;
	
	// Constructor
	public Task(String title, String description, LocalDate dueDate, LocalTime dueTime, TaskStatus status) {
		this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.status = status;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public LocalTime getDueTime() {
		return dueTime;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

}
