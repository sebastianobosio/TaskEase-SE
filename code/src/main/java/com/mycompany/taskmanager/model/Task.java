package com.mycompany.taskmanager.model;

import java.util.Date;

public class Task {
	public enum TaskStatus {
		COMPLETED,
		ONGOING,
		NOTSTARTED,	
	}
	
	private String title;
	private String description;
	private Date dueDate;
	private TaskStatus status;
	
	// Constructor
	public Task(String title, String description, Date dueDate, TaskStatus status) {
		this.title = title;
        this.description = description;
        this.dueDate = dueDate;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

}
