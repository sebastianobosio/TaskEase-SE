package com.mycompany.taskmanager.view;

import com.mycompany.taskmanager.model.Task;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

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
        taskStatusLabel = new JLabel("Status: " + task.getStatus());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        taskDueDateLabel = new JLabel("Due date: " + dateFormat.format(task.getDueDate()));
        
        // Set up layout   
        setLayout(new BorderLayout());
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(taskTitleLabel);
        infoPanel.add(taskStatusLabel);
        infoPanel.add(taskDueDateLabel);
        infoPanel.setPreferredSize(new Dimension(100, 100));
        add(infoPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(100, 100));
    }
    
    public JLabel getTaskTitle() {
        return taskTitleLabel;
    }
    
    public Task getTask() {
    	return task;
    }
}
