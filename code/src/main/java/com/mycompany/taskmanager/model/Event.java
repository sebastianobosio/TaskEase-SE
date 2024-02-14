package com.mycompany.taskmanager.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event {
	
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	private String title;
    private String description;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private String location;
    private int id;    
    
    // Constructor
    public Event(String title, String description, String location, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.location = location;
    }
	    
	// Constructor for events retrieved from the database
    public Event(int id, String title, String description, String location, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        this.id = id;
    	this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.location = location;
    }
    
    public boolean isEventOverdue() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        
        // Check if the due date is before today's date
        if (endDate.isBefore(today)) {
            return true;
        } else if (endDate.isEqual(today)) {
            // If the due date is today, check if the due time has passed
            return endTime.isBefore(now);
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public String getFormattedStartDate() {
        return startDate.format(dateFormatter);
    }

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public String getFormattedStartTime() {
        return startTime.format(timeFormatter);
    }

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public String getFormattedEndDate() {
        return endDate.format(dateFormatter);
    }

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getFormattedEndTime() {
        return endTime.format(timeFormatter);
    }
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
