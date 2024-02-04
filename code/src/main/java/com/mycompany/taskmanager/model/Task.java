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

	public String getStatus() {
	    String statusString = null;
	    
	    if (status == TaskStatus.COMPLETED) {
	        statusString = "Completed";
	    } else if (status == TaskStatus.NOTSTARTED) {
	        statusString = "Not Started";
	    } else if (status == TaskStatus.ONGOING) {
	        statusString = "In Progress";
	    }
	    
	    return statusString;
	}  

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

}
