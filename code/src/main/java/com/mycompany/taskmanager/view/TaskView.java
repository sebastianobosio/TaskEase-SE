package com.mycompany.taskmanager.view;

import com.mycompany.taskmanager.model.Task;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class TaskView extends JPanel {
    private JLabel taskTitleLabel;
    private JLabel taskDueDateLabel;
    private JLabel taskDueTimeLabel;
    private JLabel taskStatusLabel;
    private Task task;
    
    public TaskView(Task task) {
    	this.task = task;
    	
        // Initialize components
        taskTitleLabel = new JLabel(task.getTitle());
        taskStatusLabel = new JLabel("Status: " + task.getStatus());
        taskDueDateLabel = new JLabel("Due date: " + task.getFormattedDueDate());
        taskDueTimeLabel = new JLabel("Due time: " + task.getFormattedDueTime());
        
        // customize label
        taskTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        taskStatusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taskDueDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taskDueTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        taskTitleLabel.setForeground(new Color(50, 50, 50));
        taskStatusLabel.setForeground(new Color(80, 80, 80));
        taskDueDateLabel.setForeground(new Color(80, 80, 80));
        taskDueTimeLabel.setForeground(new Color(80, 80, 80));
        
        // Set up layout   
        setLayout(new BorderLayout());
        // Create and style the info panel
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.setBackground(new Color(173, 216, 230));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding

        infoPanel.add(taskTitleLabel);
        infoPanel.add(taskStatusLabel);
        infoPanel.add(taskDueDateLabel);
        infoPanel.add(taskDueTimeLabel);
        
        add(infoPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(400, 100));
        setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
    }
    
    public JLabel getTaskTitle() {
        return taskTitleLabel;
    }
    
    public Task getTask() {
    	return task;
    }
}
