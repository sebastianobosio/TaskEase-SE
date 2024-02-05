package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.TaskView;
import com.mycompany.database.SQLiteTaskDAO;
import com.mycompany.taskmanager.model.Task;
import com.mycompany.taskmanager.model.Task.TaskStatus;
import com.mycompany.taskmanager.view.DetailedTaskView;
import com.mycompany.taskmanager.view.MainView;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class TaskController {
	private TaskView taskView; // used for edit existing task
	private DetailedTaskView detailedTaskView; // used for creation of new task
	private MainView mainView;
	
	// it adds the title click and consequently the save and delete listener on the created detailed view
    public TaskController(TaskView taskView, MainView mainView) {
        this.taskView = taskView;
        this.mainView = mainView;
        taskView.getTaskTitle().addMouseListener(new TaskViewMouseListener());
    }
    
    // Controller for the creation of the new Task, it adds the save and delete listenere, but not the listener o
    // on the title click
    public TaskController(DetailedTaskView detailedTaskView, MainView mainView) {
    	this.detailedTaskView = detailedTaskView;
    	this.mainView = mainView;
    	detailedTaskView.getSaveButton().addActionListener(new SaveButtonListener(detailedTaskView, mainView));
    	detailedTaskView.getDeleteButton().addActionListener(new DeleteButtonListener(detailedTaskView, mainView));
    }
    
    private class TaskViewMouseListener extends MouseAdapter {
    	public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) { // Single-click
                handleTaskClick();
            }
    	}		
    }
    
    public void handleTaskClick() {
        Task task = taskView.getTask(); // Retrieve the task from the TaskView
        if (task != null) {
            // Open a new window or dialog to display detailed information about the task
            DetailedTaskView detailedTaskView = new DetailedTaskView(task);
            // add listener to the save button
            detailedTaskView.getSaveButton().addActionListener(new SaveButtonListener(detailedTaskView, mainView));
            detailedTaskView.getDeleteButton().addActionListener(new DeleteButtonListener(detailedTaskView, mainView));
            detailedTaskView.setVisible(true);
        }
    }
    
    // Action listener for the save button
    private class SaveButtonListener implements ActionListener {
    	private DetailedTaskView detailedTaskView;
    	private MainView mainView;
    	
    	public SaveButtonListener(DetailedTaskView detailedTaskView, MainView mainView) {
    		this.detailedTaskView = detailedTaskView;
    		detailedTaskView.setVisible(true);
    		this.mainView = mainView;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	SQLiteTaskDAO sqliteTaskDAO = new SQLiteTaskDAO();
        	String operation = "update";
        	
        	// get field values
        	String title = detailedTaskView.getTitleFieldValue();
    		String description = detailedTaskView.getDescriptionAreaValue();
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

