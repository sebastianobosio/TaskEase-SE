package com.mycompany.taskmanager.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
	 private String title;
	    private String description;
	    private LocalDate startDate;
	    private LocalTime startTime;
	    private LocalDate endDate;
	    private LocalTime endTime;
	    private String location;

	    // Constructor
	    public Event(String title, String description, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, String location) {
	        this.title = title;
	        this.description = description;
	        this.startDate = startDate;
	        this.startTime = startTime;
	        this.endDate = endDate;
	        this.endTime = endTime;
	        this.location = location;
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

		public LocalTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalTime startTime) {
			this.startTime = startTime;
		}

		public LocalDate getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}

		public LocalTime getEndTime() {
			return endTime;
		}

		public void setEndTime(LocalTime endTime) {
			this.endTime = endTime;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}
}
