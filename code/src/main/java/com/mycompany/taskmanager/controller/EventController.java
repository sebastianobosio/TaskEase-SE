package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.EventView;
import com.mycompany.taskmanager.view.MainView;
import com.mycompany.database.SQLiteEventDAO;
import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.view.DetailedEventView;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
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
        	String operation = "update";
        	
        	// get field values
        	String title = detailedEventView.getTitleFieldValue();
    		String description = detailedEventView.getDescriptionAreaValue();
    		String location = detailedEventView.getLocationAreaValue();
    		System.out.println(description);
    		LocalDate startDate = detailedEventView.getStartDateFieldValue();
    		LocalTime startTime = detailedEventView.getStartTimeFieldValue();
    		LocalDate endDate = detailedEventView.getEndDateFieldValue();
    		LocalTime endTime = detailedEventView.getEndTimeFieldValue();
    		
        	if (detailedEventView.getEvent() == null) {
        		operation = "create";
        		// i have to check the field values.
        		Event newEvent = new Event(title, description, location, startDate, startTime, endDate, endTime);
        		// save event and update view panel
        		int newEventID = sqliteEventDAO.saveEvent(newEvent);
        		Event newEventWithID = sqliteEventDAO.getEventById(newEventID);
        		// i have to add the event with the ID retrieved from the DB to make the update operation possible
        		mainView.addEventView(newEventWithID);
        	}
        	
        	if (operation == "update") {
        		Event event = detailedEventView.getEvent();
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
        	
        	mainView.updateContentPanel();
        	detailedEventView.dispose();
        	// Update task details based on user input
            //task.setTitle(titleField.getText());
            //task.setDescription(descriptionArea.getText());
            // Parse and set due date from dueDateField
            // Set task status from statusComboBox.getSelectedItem()
            // date field should be parsed as Date(string). 
            // save operation
        	// check on the fields. If one is empty saves not possible-> open an error window
        	// check on detailedTaskView.getTask(), if it returns the Task instance then the task exist
        	// so the save operation is a modification of the DB.
        	// if it does not return a Task then the DetailedTaskView was created via the CreateTaskButton
        	// and so it use a different constructor. So the save is an add to the DB.
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
