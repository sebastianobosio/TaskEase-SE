package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.TaskView;
import com.mycompany.taskmanager.model.Task;
import com.mycompany.taskmanager.view.DetailedTaskView;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class TaskController {
	private TaskView taskView; // used for edit existing task
	private DetailedTaskView detailedTaskView; // used for creation of new task
	
	// it adds the title click and consequently the save and delete listener on the created detailed view
    public TaskController(TaskView taskView) {
        this.taskView = taskView;
        
        taskView.getTaskTitle().addMouseListener(new TaskViewMouseListener());
    }
    
    // Controller for the creation of the new Task, it adds the save and delete listenere, but not the listener o
    // on the title click
    public TaskController(DetailedTaskView detailedTaskView) {
    	this.detailedTaskView = detailedTaskView;
    	//detailedTaskView.getSaveButton().addActionListener(new SaveButtonListener(detailedTaskView));
    	//detailedTaskView.getDeleteButton().addActionListener(new SaveButtonListener(detailedTaskView));
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
            //detailedTaskView.getSaveTaskButton().addActionListener(new SaveButtonListener(detailedTaskView));
            //detailedTaskView.getDeleteTaskButton().addActionListener(new DeleteButtonListener(detailedTaskView));
            detailedTaskView.setVisible(true);
        }
    }
    
    // Action listener for the save button
    private class SaveButtonListener implements ActionListener {
    	private DetailedTaskView detailedTaskView;

    	public SaveButtonListener(DetailedTaskView detailedTaskView) {
    		this.detailedTaskView = detailedTaskView;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
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
    
    // Action listenere for the delete button
    private class DeleteButtonListener implements ActionListener {
    	private DetailedTaskView detailedTaskView;

    	public DeleteButtonListener(DetailedTaskView detailedTaskView) {
    		this.detailedTaskView = detailedTaskView;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
            // delete operation
        	// if it's already created delete from db, if not yet created just close the window
        }
    }
}

