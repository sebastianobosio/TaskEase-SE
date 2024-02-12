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

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
            // add listener to the save and delete button
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
        	
        	// get field values
        	String title = detailedTaskView.getTitleFieldValue();
    		String description = detailedTaskView.getDescriptionAreaValue();
    		String statusAsString = detailedTaskView.getStatusComboBoxValue();
    		LocalDate dueDate = detailedTaskView.getDueDateFieldValue();
    		LocalTime dueTime = detailedTaskView.getDueTimeFieldValue();
    		// Validate fields
    	    if (!validateFields(title, description, statusAsString, dueDate, dueTime)) {
    	        return;
    	    }

            Task task = detailedTaskView.getTask();
            if (task == null) {
                createNewTask(sqliteTaskDAO, title, description, statusAsString, dueDate, dueTime);
            } else {
                updateTask(sqliteTaskDAO, task, title, description, statusAsString, dueDate, dueTime);
            }
        	
        	mainView.updateContentPanel();
        	detailedTaskView.dispose();
        }

		private void updateTask(SQLiteTaskDAO sqliteTaskDAO, Task task, String title, String description,
				String statusAsString, LocalDate dueDate, LocalTime dueTime) {
			mainView.deleteTaskView(task);
    		task.setTitle(title);
    		task.setDescription(description);
    		task.setDueDate(dueDate);
    		task.setDueTime(dueTime);
    		TaskStatus status = convertStatus(statusAsString);
    		task.setStatus(status);
    		sqliteTaskDAO.updateTask(task);
    		//if update is successful
    		mainView.addTaskView(task);
			
		}

		private void createNewTask(SQLiteTaskDAO sqliteTaskDAO, String title, String description, String statusAsString,
				LocalDate dueDate, LocalTime dueTime) {
			TaskStatus status = convertStatus(statusAsString);
			// i have to check the field values.
    		Task newTask = new Task(title, description, dueDate, dueTime, status);
    		// save task and update view panel
    		int newTaskID = sqliteTaskDAO.saveTask(newTask);
    		Task newTaskWithID = sqliteTaskDAO.getTaskById(newTaskID);
    		// i have to add the task with the ID retrieved from the DB to make the update operation possible
    		mainView.addTaskView(newTaskWithID);
		}

		private boolean validateFields(String title, String description, String statusAsString, LocalDate dueDate,
				LocalTime dueTime) {
			
			String message = null;
			
			if (title.isEmpty()) {
	            message = "Title cannot be empty.";
	        } else if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
	            message = "Due date must be in the future or not empty";
	        } else if (dueTime == null) {
	            message = "Due time cannot be empty.";
	        } else {
	            TaskStatus status = convertStatus(statusAsString);
	            if (status == null) {
	                message = "Invalid status.";
	            }
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

		private TaskStatus convertStatus(String statusAsString) {
			switch (statusAsString) {
	        case "Completed":
	            return TaskStatus.COMPLETED;
	        case "Not Started":
	            return TaskStatus.NOTSTARTED;
	        case "In Progress":
	            return TaskStatus.ONGOING;
	        default:
	            return null;
			}
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

