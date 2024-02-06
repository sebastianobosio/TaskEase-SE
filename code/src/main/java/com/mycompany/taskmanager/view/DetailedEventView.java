package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.mycompany.taskmanager.model.Event;

public class DetailedEventView extends JFrame {
	
	private Event event; // Store the task data
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
	private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField startDateField;
    private JTextField startTimeField;
    private JTextField endDateField;
    private JTextField endTimeField;
    private JTextArea locationArea;
    private JButton saveButton;
    private JButton deleteButton;
    
    // Constructor for creation of new events
    public DetailedEventView() {
        initializeFrame();
        initializeComponents();
    }
    
    // Constructor for existing task, used for editing
    public DetailedEventView(Event event) {
        this.event = event;
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
        startDateField = new JTextField();
        startTimeField = new JTextField();
        endDateField = new JTextField();
        endTimeField = new JTextField();
        locationArea = new JTextArea("", 5, 20);
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        
        // Set up the layout
        JPanel panel = new JPanel(new GridLayout(9, 2));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(new JLabel("Start Date:"));
        panel.add(startDateField);
        panel.add(new JLabel("Start Time:"));
        panel.add(startTimeField);
        panel.add(new JLabel("End Date:"));
        panel.add(endDateField);
        panel.add(new JLabel("End Time:"));
        panel.add(endTimeField);
        panel.add(new JLabel("Location"));
        panel.add(locationArea);
        panel.add(saveButton);
        panel.add(deleteButton);
        
        add(panel);
    }
	
	private void populateFields() {
        titleField.setText(event.getTitle());
        descriptionArea.setText(event.getDescription());
        startDateField.setText(event.getStartDate().toString());
        startTimeField.setText(event.getStartTime().toString());
        endDateField.setText(event.getEndDate().toString());
        endTimeField.setText(event.getEndTime().toString());
        locationArea.setText(event.getLocation());
    }
	
	// Method to read the value of titleField
    public String getTitleFieldValue() {
        return titleField.getText();
    }

    // Method to read the value of descriptionArea
    public String getDescriptionAreaValue() {
        return descriptionArea.getText();
    }

    // Method to read the value of startDateField as LocalDate
    public LocalDate getStartDateFieldValue() {
        return LocalDate.parse(startDateField.getText(), dateFormatter);
    }

    // Method to read the value of startTimeField as LocalTime
    public LocalTime getStartTimeFieldValue() {
        return LocalTime.parse(startTimeField.getText(), timeFormatter);
    }
    
    // Method to read the value of endDateField as LocalDate
    public LocalDate getEndDateFieldValue() {
        return LocalDate.parse(endDateField.getText(), dateFormatter);
    }
    
    // Method to read the value of endTimeField as LocalTime
    public LocalTime getEndTimeFieldValue() {
        return LocalTime.parse(endTimeField.getText(), timeFormatter);
    }
	
	public JButton getSaveButton() {
    	return saveButton;
    }
    
    public JButton getDeleteButton() {
    	return deleteButton;
    }
    
	public Event getTask() {
    	return event;
    }
}
