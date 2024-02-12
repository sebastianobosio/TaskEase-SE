package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.mycompany.taskmanager.model.Task;

public class DetailedTaskView extends JFrame {
	
	private Task task; // Store the task data
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField dueDateField;
    private JTextField dueTimeField;
    private JComboBox<String> statusComboBox;
    private JButton saveButton;
    private JButton deleteButton;
    
    // Constructor for creation of new tasks
    public DetailedTaskView() {
        initializeFrame();
        initializeComponents();
    }
    
    // Constructor for existing task, used for editing
    public DetailedTaskView(Task task) {
        this.task = task;
        initializeFrame();
        initializeComponents();
        populateFields();
    }
    
    private void initializeFrame() {
        setTitle("Detailed View");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        titleField = new JTextField();
        descriptionArea = new JTextArea("", 5, 20);
        dueDateField = new JTextField();
        dueTimeField = new JTextField();
        statusComboBox = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        
        // Set up the layout
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(new JLabel("Due Date:"));
        panel.add(dueDateField);
        panel.add(new JLabel("Due Time:"));
        panel.add(dueTimeField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(saveButton);
        panel.add(deleteButton);
        
        add(panel);
    }
    
    private void populateFields() {
        titleField.setText(task.getTitle());
        descriptionArea.setText(task.getDescription());
        dueDateField.setText(task.getFormattedDueDate());
        dueTimeField.setText(task.getFormattedDueTime());
        statusComboBox.setSelectedItem(task.getStatus());
    }
	
    // Method to read the value of titleField
    public String getTitleFieldValue() {
        return titleField.getText();
    }

    // Method to read the value of descriptionArea
    public String getDescriptionAreaValue() {
        return descriptionArea.getText();
    }

    // Method to read the value of dueDateField as LocalDate
    public LocalDate getDueDateFieldValue() {
    	if (dueDateField.getText().trim().isEmpty()) {
    		return null;
    	}
        return LocalDate.parse(dueDateField.getText(), dateFormatter);
    }

    // Method to read the value of dueTimeField as LocalTime
    public LocalTime getDueTimeFieldValue() {
    	if (dueTimeField.getText().trim().isEmpty()) {
    		return null;
    	}
        return LocalTime.parse(dueTimeField.getText(), timeFormatter);
    }

    // Method to read the selected value from statusComboBox
    public String getStatusComboBoxValue() {
        return (String) statusComboBox.getSelectedItem();
    }
    
    public JButton getSaveButton() {
    	return saveButton;
    }
    
    public JButton getDeleteButton() {
    	return deleteButton;
    }
    
    
	public Task getTask() {
    	return task;
    }
}
