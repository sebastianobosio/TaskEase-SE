package com.mycompany.taskmanager.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Task {
	public enum TaskStatus {
		COMPLETED,
		ONGOING,
		NOTSTARTED,	
	}
	
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
	private String title;
	private String description;
	private LocalDate dueDate;
	private LocalTime dueTime;
	private TaskStatus status;
	private int id;
	
	// Constructor
	public Task(String title, String description, LocalDate dueDate, LocalTime dueTime, TaskStatus status) {
		this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.status = status;
	}
	
	// Constructor for tasks retrieved from the database
	public Task(int id, String title, String description, LocalDate dueDate, LocalTime dueTime, TaskStatus status) {
		this.id = id;
		this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.status = status;
	}
	
	public int getId() {
		return id;
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
	
	public void setDueDate(LocalDate date) {
		this.dueDate = date;
	}
	
	public String getFormattedDueDate() {
        return dueDate.format(dateFormatter);
    }
	
	public LocalTime getDueTime() {
		return dueTime;
	}
	
	public void setDueTime(LocalTime time) {
		this.dueTime = time;
	}

    public String getFormattedDueTime() {
        return dueTime.format(timeFormatter);
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
