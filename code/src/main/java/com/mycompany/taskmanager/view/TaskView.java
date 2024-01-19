package com.mycompany.taskmanager.view;

import com.mycompany.taskmanager.model.Task;

import javax.swing.*;
import java.awt.*;


public class TaskView extends JPanel {
    private JLabel taskTitleLabel;
    private JLabel taskDescriptionLabel;
    private JLabel taskStatusLabel;

    public TaskView(Task task) {
        // Initialize components
        taskTitleLabel = new JLabel(task.getTitle());
        taskDescriptionLabel = new JLabel(task.getDescription());
        taskStatusLabel = new JLabel("Status: " + task.getStatus());

        // Set up layout
        setLayout(new BorderLayout());
        add(taskTitleLabel, BorderLayout.NORTH);
        add(taskDescriptionLabel, BorderLayout.CENTER);
        add(taskStatusLabel, BorderLayout.SOUTH);
        
        setPreferredSize(new Dimension(300, 100));
    }
}
