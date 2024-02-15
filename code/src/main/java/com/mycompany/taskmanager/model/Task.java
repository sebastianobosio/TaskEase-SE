package com.mycompany.taskmanager.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Task {
	
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
	
	public boolean isTaskOverdue() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        
        // Check if the due date is before today's date
        if (dueDate.isBefore(today)) {
            return true;
        } else if (dueDate.isEqual(today)) {
            // If the due date is today, check if the due time has passed
            return dueTime.isBefore(now);
        } else {
            // If the due date is in the future, the task is not overdue
            return false;
        }
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
        switch (status) {
            case COMPLETED:
                return TaskStatus.COMPLETED.toString();
            case NOTSTARTED:
                return TaskStatus.NOTSTARTED.toString();
            case ONGOING:
                return TaskStatus.ONGOING.toString();
            default:
                throw new IllegalStateException("Unexpected status: " + status);
        }
    }

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

}
