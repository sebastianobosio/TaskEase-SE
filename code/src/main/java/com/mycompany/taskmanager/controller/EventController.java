package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.EventView;
import com.mycompany.taskmanager.view.MainView;
import com.mycompany.database.SQLiteEventDAO;
import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.view.DetailedEventView;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class EventController {
	private EventView eventView;
	private DetailedEventView detailedEventView;
	private MainView mainView;
	
	// it adds the title click and consequently the save and delete listener on the created detailed view
    public EventController(EventView eventView, MainView mainView) {
        this.eventView = eventView;
        this.mainView = mainView;
        eventView.getEventTitle().addMouseListener(new EventViewMouseListener());
    }
    
    // Controller for the creation of the new Event, it adds the save and delete listener, but not the listener
    // on the title click
    public EventController(DetailedEventView detailedEventView, MainView mainView) {
    	this.detailedEventView = detailedEventView;
    	this.mainView = mainView;
    	detailedEventView.getSaveButton().addActionListener(new SaveButtonListener(detailedEventView, mainView));
    	detailedEventView.getDeleteButton().addActionListener(new DeleteButtonListener(detailedEventView, mainView));
    }
    
    
    private class EventViewMouseListener extends MouseAdapter {
    	public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) { // Single-click
                handleEventClick();
            }
    	}		
    }
    
    public void handleEventClick() {
        Event event = eventView.getEvent(); // Retrieve the task from the TaskView
        if (event != null) {
            // Open a new window or dialog to display detailed information about the task
            DetailedEventView detailedEventView = new DetailedEventView(event);
            detailedEventView.setVisible(true);
            // add listener to the save and delete button
            detailedEventView.getSaveButton().addActionListener(new SaveButtonListener(detailedEventView, mainView));
            detailedEventView.getDeleteButton().addActionListener(new DeleteButtonListener(detailedEventView, mainView));
            detailedEventView.setVisible(true);
        }
    }
    
    // Action listener for the save button
    private class SaveButtonListener implements ActionListener {
    	private DetailedEventView detailedEventView;
    	private MainView mainView;
    	
    	public SaveButtonListener(DetailedEventView detailedEventView, MainView mainView) {
    		this.detailedEventView = detailedEventView;
    		detailedEventView.setVisible(true);
    		this.mainView = mainView;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	SQLiteEventDAO sqliteEventDAO = new SQLiteEventDAO();
        	
        	// get field values
        	String title = detailedEventView.getTitleFieldValue();
    		String description = detailedEventView.getDescriptionAreaValue();
    		String location = detailedEventView.getLocationAreaValue();
    		LocalDate startDate = detailedEventView.getStartDateFieldValue();
    		LocalTime startTime = detailedEventView.getStartTimeFieldValue();
    		LocalDate endDate = detailedEventView.getEndDateFieldValue();
    		LocalTime endTime = detailedEventView.getEndTimeFieldValue();
    		// Validate fields
    	    if (!validateFields(title, startDate, startTime, endDate, endTime)) {
    	        return;
    	    }
    	    
    	    Event event = detailedEventView.getEvent();
    	    
    	    if (event == null) {
                createNewEvent(sqliteEventDAO, title, description, location, startDate, startTime, endDate, endTime);
            } else {
                updateEvent(sqliteEventDAO, event, title, description, location, startDate, startTime, endDate, endTime);
            }   	
        	
        	mainView.updateContentPanel();
        	detailedEventView.dispose();
        }
        
        private void updateEvent(SQLiteEventDAO sqliteEventDAO, Event event, String title, String description,
				String location, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        	mainView.deleteEventView(event);
    		event.setTitle(title);
    		event.setDescription(description);
    		event.setLocation(location);
    		event.setStartDate(startDate);
    		event.setStartTime(startTime);
    		event.setEndDate(endDate);
    		event.setEndTime(endTime);
    		sqliteEventDAO.updateEvent(event);
    		//if update is successful
    		mainView.addEventView(event);
		}

		private void createNewEvent(SQLiteEventDAO sqliteEventDAO, String title, String description, String location,
				LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        	// i have to check the field values.
    		Event newEvent = new Event(title, description, location, startDate, startTime, endDate, endTime);
    		// save event and update view panel
    		int newEventID = sqliteEventDAO.saveEvent(newEvent);
    		Event newEventWithID = sqliteEventDAO.getEventById(newEventID);
    		// i have to add the event with the ID retrieved from the DB to make the update operation possible
    		mainView.addEventView(newEventWithID);
		}

		private boolean validateFields(String title, LocalDate startDate, LocalTime startTime, 
        		LocalDate endDate, LocalTime endTime) {
			
			String message = null;
			
			if (title.isEmpty()) {
		        message = "Title cannot be empty.";
		    } else if (startDate == null || startDate.isBefore(LocalDate.now())) {
		        message = "Start date must be in the future and not empty.";
		    } else if (startTime == null) {
		        message = "Mispelled start time or empty.";
		    } else if (endDate == null) {
		        message = "Mispelled end date or empty.";
		    } else if (endTime == null) {
		        message = "Mispelled end time or empty.";
		    } else if (endDate.isBefore(startDate) || (endDate.isEqual(startDate) && endTime.isBefore(startTime))) {
		        message = "End date and time must be after start date and time.";
		    }

	        if (message != null) {
	            displayErrorWindow(message);
	            return false;
	        }

		    return true;
		}
        
        private void displayErrorWindow(String message) {
			JFrame frame = new JFrame("Error");
	        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	    }
    }
    
    
    
    // Action listener for the delete button
    private class DeleteButtonListener implements ActionListener {
    	private DetailedEventView detailedEventView;
    	private MainView mainView;
    	
    	public DeleteButtonListener(DetailedEventView detailedEventView, MainView mainView) {
    		this.detailedEventView = detailedEventView;
    		this.mainView = mainView;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	SQLiteEventDAO sqliteEventDAO = new SQLiteEventDAO();
        	String operation = "remove";
        	
        	// the task is in not yet created, so just close the window
        	if (detailedEventView.getEvent() == null) {
        		operation = "close window";
        	}
        	
        	if (operation == "remove") {
        		Event eventToRemove = detailedEventView.getEvent();
        		int eventToRemoveID = eventToRemove.getId();
        		sqliteEventDAO.deleteEvent(eventToRemoveID);
        		mainView.deleteEventView(eventToRemove);
        	}
        	mainView.updateContentPanel();
        	detailedEventView.dispose();
        }
    }
}
