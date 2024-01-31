package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import com.mycompany.taskmanager.model.Task;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailedTaskView extends JFrame {
	
	private Task task; // Store the task data

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField dueDateField;
    private JComboBox<String> statusComboBox;
    
    
	public DetailedTaskView(Task task) {
		this.task = task;
        // Set up the frame
        setTitle("Detailed View");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components for task details
        titleField = new JTextField(task.getTitle());
        descriptionArea = new JTextArea(task.getDescription(), 5, 20);
        dueDateField = new JTextField(task.getDueDate().toString());
        statusComboBox = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});
        statusComboBox.setSelectedItem(task.getStatus());
        
        // Create a save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update task details based on user input
                task.setTitle(titleField.getText());
                task.setDescription(descriptionArea.getText());
                // Parse and set due date from dueDateField
                // Set task status from statusComboBox.getSelectedItem()
                // date field should be parsed as Date(string). 
                // the save should delete the taskView and replace with a new taskView built with the new
                // field data and add it to the database. This part should be moved to a controller
                // Close the frame after saving changes
                dispose();
            }
        });

        // Create a panel to organize components
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(new JLabel("Due Date:"));
        panel.add(dueDateField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(saveButton);

        add(panel);
    }
}
