package com.mycompany.taskmanager.view;

import com.mycompany.taskmanager.model.Task;

import javax.swing.*;
import java.awt.*;


public class TaskView extends JPanel {
    private JLabel taskTitleLabel;
    //private JLabel taskDescriptionLabel;
    private JLabel taskDueDateLabel;
    private JLabel taskStatusLabel;
    private Task task;
    
    public TaskView(Task task) {
    	this.task = task;
        // Initialize components
        taskTitleLabel = new JLabel(task.getTitle());
        //taskDescriptionLabel = new JLabel(task.getDescription());
        taskStatusLabel = new JLabel("Status: " + task.getStatus());
        taskDueDateLabel = new JLabel("Due date: " + task.getDueDate().toString());

        // Set up layout
        setLayout(new GridLayout(3,1));
        add(taskTitleLabel);
        //add(taskDescriptionLabel, BorderLayout.CENTER);
        add(taskStatusLabel);
        add(taskDueDateLabel);
        
        setPreferredSize(new Dimension(300, 100));
    }
    
    public JLabel getTaskTitle() {
        return taskTitleLabel;
    }
    
    public Task getTask() {
    	return task;
    }
}
