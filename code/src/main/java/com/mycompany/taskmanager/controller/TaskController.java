package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.TaskView;
import com.mycompany.taskmanager.model.Task;
import com.mycompany.taskmanager.view.DetailedTaskView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class TaskController {
	private TaskView taskView;

    public TaskController(TaskView taskView) {
        this.taskView = taskView;
        
        taskView.getTaskTitle().addMouseListener(new TaskViewMouseListener());
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
            detailedTaskView.setVisible(true);
        }
    }
}

