package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import com.mycompany.taskmanager.model.Task;

public class DetailedTaskView extends JFrame {
	
	public DetailedTaskView(Task task) {
        // Set up the frame
        setTitle("Detailed View");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and add components to display detailed task information
        JLabel titleLabel = new JLabel("Title: " + task.getTitle());
        JLabel descriptionLabel = new JLabel("Description: " + task.getDescription());
        JLabel dueDateLabel = new JLabel("Date: " + task.getDueDate().toString());
        JLabel statusLabel = new JLabel("Status: " + task.getStatus());

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(titleLabel);
        panel.add(descriptionLabel);
        panel.add(dueDateLabel);
        panel.add(statusLabel);

        add(panel);
    }
}
