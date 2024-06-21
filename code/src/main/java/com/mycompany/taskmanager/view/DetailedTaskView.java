package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import com.mycompany.taskmanager.model.Task;

public class DetailedTaskView extends JFrame {
	
	private Task task; // Store the task data

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JDatePickerImpl dueDatePicker;
    private JSpinner dueTimeSpinner;
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
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
    	titleField = new JTextField();
	    descriptionArea = new JTextArea(3, 10);
        statusComboBox = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});
        
        // Set font for title field
	    titleField.setFont(new Font("Arial", Font.BOLD, 16));
	    titleField.setBackground(new Color(240, 240, 240));
	    titleField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
	    // Set font, border and background for description field
	    descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
	    descriptionArea.setBackground(new Color(240, 240, 240));
	    descriptionArea.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
	    // set status combo box
	    statusComboBox.setBackground(new Color(240, 240, 240));
	    statusComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
	    
	    // Buttons
	    saveButton = new JButton("Save");
	    saveButton.setBackground(new Color(240, 240, 240));
	    deleteButton = new JButton("Delete");
	    deleteButton.setBackground(new Color(240, 240, 240));
	    
        //due date picker
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        
        UtilDateModel dateModel = new UtilDateModel();

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        
        dueDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        dueDatePicker.setBackground(new Color(240, 240, 240));
        
        //due time picker
        SpinnerDateModel spinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        dueTimeSpinner = new JSpinner(spinnerModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(dueTimeSpinner, "HH:mm");
        dueTimeSpinner.setEditor(timeEditor);
        JFormattedTextField timeTextField = ((JSpinner.DefaultEditor) dueTimeSpinner.getEditor()).getTextField();
        timeTextField.setBackground(new Color(240, 240, 240));
               
        // Main panel setup with GridBagLayout for more control
	    JPanel mainPanel = new JPanel(new GridBagLayout());
	    mainPanel.setBackground(new Color(240, 240, 240));
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.WEST;
	    gbc.insets = new Insets(5, 10, 5, 10); // Padding

	    // Title
	    JLabel titleLabel = new JLabel("Title:");
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
	    gbc.gridwidth = 2;
	    gbc.fill = GridBagConstraints.BOTH;
	    mainPanel.add(titleLabel, gbc);
	    gbc.gridy++;
	    mainPanel.add(titleField, gbc);
	    gbc.gridx = 0;
	    gbc.gridy++;

	    // Description
	    JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(descriptionLabel, gbc);
        gbc.gridy++;
        mainPanel.add(new JScrollPane(descriptionArea), gbc);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy++;

	    // Date, Time and Status panel setup
	    JPanel dateTimePanel = new JPanel(new GridBagLayout());
	    dateTimePanel.setBackground(new Color(173, 216, 230));
	    GridBagConstraints gbcDateTime = new GridBagConstraints();
	    gbcDateTime.gridx = 0;
	    gbcDateTime.gridy = 0;
	    gbcDateTime.anchor = GridBagConstraints.WEST;
	    gbcDateTime.insets = new Insets(5, 10, 5, 10); // Padding

	    // Due Date
	    JLabel startDateLabel = new JLabel("Due Date:");
	    startDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    dateTimePanel.add(startDateLabel, gbcDateTime);
	    gbcDateTime.gridx++;
	    dateTimePanel.add(dueDatePicker, gbcDateTime);
	    gbcDateTime.gridx = 0;
	    gbcDateTime.gridy++;

	    // Due Time
	    JLabel startTimeLabel = new JLabel("Due Time:");
	    startTimeLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    dateTimePanel.add(startTimeLabel, gbcDateTime);
	    gbcDateTime.gridx++;
	    dateTimePanel.add(dueTimeSpinner, gbcDateTime);
	    gbcDateTime.gridx = 0;
	    gbcDateTime.gridy++;
	    
	    // Status
	    JLabel statusLabel = new JLabel("Status:");
	    startTimeLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    dateTimePanel.add(statusLabel, gbcDateTime);
	    gbcDateTime.gridx++;
	    dateTimePanel.add(statusComboBox, gbcDateTime);
	    gbcDateTime.gridx = 0;
	    gbcDateTime.gridy++;

	    // Add date, time and status panel to main panel
	    gbc.gridx = 0;
	    gbc.gridwidth = 2;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    mainPanel.add(dateTimePanel, gbc);
	    gbc.gridy++;

	    // Button panel setup
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        buttonPanel.setBackground(new Color(100, 100, 100));

	    buttonPanel.add(saveButton);
	    buttonPanel.add(deleteButton);

	    // Add button panel to main panel
	    gbc.anchor = GridBagConstraints.CENTER;
	    gbc.gridwidth = 2;
	    gbc.gridy++;
	    mainPanel.add(buttonPanel, gbc);

	    // Add main panel to the current container (assuming it's a JPanel or similar)
	    add(mainPanel);
    }
    
    private void populateFields() {
        titleField.setText(task.getTitle());
        descriptionArea.setText(task.getDescription());
        if (task.getDueDate() != null) {
            LocalDate dueDate = task.getDueDate();
            dueDatePicker.getModel().setDate(
                dueDate.getYear(),
                dueDate.getMonthValue() - 1, // Adjust for zero-based month
                dueDate.getDayOfMonth()
            );
            dueDatePicker.getModel().setSelected(true);
        }
        if (task.getDueTime() != null) {
        	try {
            String timeString = task.getFormattedDueTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date selectedTime = dateFormat.parse(timeString);
            dueTimeSpinner.setValue(selectedTime);
        	} catch (ParseException e) {
                e.printStackTrace();
        	}
        }
        statusComboBox.setSelectedItem(task.getStatus().toString());
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
    	int year = dueDatePicker.getModel().getYear();
    	int month = dueDatePicker.getModel().getMonth() + 1; // Adjust for zero-based month (January is the 0)
    	int day = dueDatePicker.getModel().getDay();
    	try {
    		//String dateString = String.format("%04d-%02d-%02d", year, month, day);
    		LocalDate selectedDate = LocalDate.of(year, month, day);
    		return selectedDate;
    	} catch (DateTimeParseException e) {
            System.out.println("Error parsing due date: " + e.getMessage());
            return null;
    	}
    }

    // Method to read the value of dueTimeField as LocalTime
    public LocalTime getDueTimeFieldValue() {
    	//working with date and time is a struggle :(
    	Date selectedDate = (Date) dueTimeSpinner.getValue();
        // Convert the Date to Instant
        Instant instant = selectedDate.toInstant();
        // Create a LocalDateTime using the Instant and the default time zone
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // Extract the LocalTime from the LocalDateTime
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime;
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
