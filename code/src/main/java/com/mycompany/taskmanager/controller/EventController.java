package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.EventView;
import com.mycompany.taskmanager.view.MainView;
import com.mycompany.database.SQLiteTaskDAO;
import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.model.Task;
import com.mycompany.taskmanager.model.Task.TaskStatus;
import com.mycompany.taskmanager.view.DetailedEventView;
import com.mycompany.taskmanager.view.DetailedTaskView;

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
    		String location = detailedEventView.ge;
    		System.out.println(description);
    		String statusAsString = detailedTaskView.getStatusComboBoxValue();
    		TaskStatus status = null;
    		if (statusAsString == "Completed") {
    			status = TaskStatus.COMPLETED;
    	    } else if (statusAsString == "Not Started") {
    	    	status = TaskStatus.NOTSTARTED;
    	    } else if (statusAsString == "In Progress") {
    	    	status = TaskStatus.ONGOING;
    	    }
    		System.out.println("i'm here");
    		LocalDate dueDate = detailedTaskView.getDueDateFieldValue();
    		LocalTime dueTime = detailedTaskView.getDueTimeFieldValue();
    		
        	if (detailedTaskView.getTask() == null) {
        		operation = "create";
        		// i have to check the field values.
        		Task newTask = new Task(title, description, dueDate, dueTime, status);
        		// save task and update view panel
        		int newTaskID = sqliteTaskDAO.saveTask(newTask);
        		Task newTaskWithID = sqliteTaskDAO.getTaskById(newTaskID);
        		// i have to add the task with the ID retrieved from the DB to make the update operation possible
        		mainView.addTaskView(newTaskWithID);
        	}
        	
        	if (operation == "update") {
        		Task task = detailedTaskView.getTask();
        		mainView.deleteTaskView(task);
        		task.setTitle(title);
        		task.setDescription(description);
        		task.setDueDate(dueDate);
        		task.setDueTime(dueTime);
        		task.setStatus(status);
        		sqliteTaskDAO.updateTask(task);
        		//if update is successful
        		mainView.addTaskView(task);
        	}
        	
        	mainView.updateContentPanel();
        	detailedTaskView.dispose();
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
    	private DetailedTaskView detailedTaskView;
    	private MainView mainView;
    	
    	public DeleteButtonListener(DetailedTaskView detailedTaskView, MainView mainView) {
    		this.detailedTaskView = detailedTaskView;
    		this.mainView = mainView;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	SQLiteTaskDAO sqliteTaskDAO = new SQLiteTaskDAO();
        	String operation = "remove";
        	
        	// the task is in not yet created, so just close the window
        	if (detailedTaskView.getTask() == null) {
        		operation = "close window";
        	}
        	
        	if (operation == "remove") {
        		Task taskToRemove = detailedTaskView.getTask();
        		int taskToRemoveID = taskToRemove.getId();
        		sqliteTaskDAO.deleteTask(taskToRemoveID);
        		mainView.deleteTaskView(taskToRemove);
        	}
        	mainView.updateContentPanel();
        	detailedTaskView.dispose();
        }
    }
}
