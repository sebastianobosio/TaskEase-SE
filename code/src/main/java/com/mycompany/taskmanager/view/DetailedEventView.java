package com.mycompany.taskmanager.view;

import javax.swing.*;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mycompany.taskmanager.model.Event;

public class DetailedEventView extends JFrame {
	
	private Event event; // Store the task data
    
	private JTextField titleField;
    private JTextArea descriptionArea;
    private JDatePickerImpl startDatePicker;
    private JSpinner startTimeSpinner;
    private JDatePickerImpl endDatePicker;
    private JSpinner endTimeSpinner;
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
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
	
	
	private void initializeComponents() {
	    // Text fields and text areas
	    titleField = new JTextField();
	    descriptionArea = new JTextArea(3, 10);
	    locationArea = new JTextArea(3, 10);

	    // Set font for title field
	    titleField.setFont(new Font("Arial", Font.BOLD, 16));
	    titleField.setBackground(new Color(240, 240, 240));
	    titleField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
	    // Set font, border and background for description field
	    descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
	    descriptionArea.setBackground(new Color(240, 240, 240));
	    descriptionArea.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
	    // Set font, border and background for location field
	    locationArea.setFont(new Font("Arial", Font.PLAIN, 14));
	    locationArea.setBackground(new Color(240, 240, 240));
	    locationArea.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));

	    
	    // Buttons
	    saveButton = new JButton("Save");
	    saveButton.setBackground(new Color(240, 240, 240));
	    deleteButton = new JButton("Delete");
	    deleteButton.setBackground(new Color(240, 240, 240));

	    // Date picker setup
	    Properties properties = new Properties();
	    properties.put("text.today", "Today");
	    properties.put("text.month", "Month");
	    properties.put("text.year", "Year");

	    UtilDateModel startDateModel = new UtilDateModel();
	    UtilDateModel endDateModel = new UtilDateModel();

	    JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, properties);
	    JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, properties);

	    startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
	    startDatePicker.setBackground(new Color(240, 240, 240));
	    endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
	    endDatePicker.setBackground(new Color(240, 240, 240));
	    
	    // Time picker setup
	    SpinnerDateModel startSpinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
	    SpinnerDateModel endSpinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);

	    startTimeSpinner = new JSpinner(startSpinnerModel);
	    endTimeSpinner = new JSpinner(endSpinnerModel);

	    JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
	    JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");

	    startTimeSpinner.setEditor(startTimeEditor);
	    endTimeSpinner.setEditor(endTimeEditor);

	    // Set the background color of the JFormattedTextField inside the spinner editor
	    JFormattedTextField startTimeTextField = ((JSpinner.DefaultEditor) startTimeSpinner.getEditor()).getTextField();
	    startTimeTextField.setBackground(new Color(240, 240, 240));

	    JFormattedTextField endTimeTextField = ((JSpinner.DefaultEditor) endTimeSpinner.getEditor()).getTextField();
	    endTimeTextField.setBackground(new Color(240, 240, 240));

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

        // Location
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(locationLabel, gbc);
        gbc.gridy++;
        mainPanel.add(new JScrollPane(locationArea), gbc);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy++;

	    // Date and Time panel setup
	    JPanel dateTimePanel = new JPanel(new GridBagLayout());
	    dateTimePanel.setBackground(new Color(144, 238, 144));
	    GridBagConstraints gbcDateTime = new GridBagConstraints();
	    gbcDateTime.gridx = 0;
	    gbcDateTime.gridy = 0;
	    gbcDateTime.anchor = GridBagConstraints.WEST;
	    gbcDateTime.insets = new Insets(5, 10, 5, 10); // Padding

	    // Start Date
	    JLabel startDateLabel = new JLabel("Start Date:");
	    startDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    dateTimePanel.add(startDateLabel, gbcDateTime);
	    gbcDateTime.gridx++;
	    dateTimePanel.add(startDatePicker, gbcDateTime);
	    gbcDateTime.gridx = 0;
	    gbcDateTime.gridy++;

	    // Start Time
	    JLabel startTimeLabel = new JLabel("Start Time:");
	    startTimeLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    dateTimePanel.add(startTimeLabel, gbcDateTime);
	    gbcDateTime.gridx++;
	    dateTimePanel.add(startTimeSpinner, gbcDateTime);
	    gbcDateTime.gridx = 0;
	    gbcDateTime.gridy++;

	    // End Date
	    JLabel endDateLabel = new JLabel("End Date:");
	    endDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    dateTimePanel.add(endDateLabel, gbcDateTime);
	    gbcDateTime.gridx++;
	    dateTimePanel.add(endDatePicker, gbcDateTime);
	    gbcDateTime.gridx = 0;
	    gbcDateTime.gridy++;

	    // End Time
	    JLabel endTimeLabel = new JLabel("End Time:");
	    endTimeLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    dateTimePanel.add(endTimeLabel, gbcDateTime);
	    gbcDateTime.gridx++;
	    dateTimePanel.add(endTimeSpinner, gbcDateTime);
	    gbcDateTime.gridx = 0;
	    gbcDateTime.gridy++;

	    // Add date and time panel to main panel
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
        titleField.setText(event.getTitle());
        descriptionArea.setText(event.getDescription());
        setDateInDatePicker(startDatePicker, event.getStartDate());
        setTimeSpinnerValue(startTimeSpinner, event.getFormattedStartTime());
        setDateInDatePicker(endDatePicker, event.getEndDate());
        setTimeSpinnerValue(endTimeSpinner, event.getFormattedEndTime());
        locationArea.setText(event.getLocation());
    }
	
	private void setDateInDatePicker(JDatePickerImpl datePicker, LocalDate date) {
	    if (date != null) {
	        UtilDateModel model = (UtilDateModel) datePicker.getModel();
	        model.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
	        model.setSelected(true);
	    }
	}
	
	private void setTimeSpinnerValue(JSpinner spinner, String formattedTime) {
	    if (formattedTime != null) {
	        try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	            Date selectedTime = dateFormat.parse(formattedTime);
	            spinner.setValue(selectedTime);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// Method to read the value of titleField
    public String getTitleFieldValue() {
        return titleField.getText();
    }

    // Method to read the value of descriptionArea
    public String getDescriptionAreaValue() {
        return descriptionArea.getText();
    }
    
    // Method to read the value of locationArea
    public String getLocationAreaValue() {
        return locationArea.getText();
    }
    
    // Use this method to get the start date field value
    public LocalDate getStartDateFieldValue() {
        return getDateFieldValue(startDatePicker);
    }

    // Use this method to get the end date field value
    public LocalDate getEndDateFieldValue() {
        return getDateFieldValue(endDatePicker);
    }
    
    private LocalDate getDateFieldValue(JDatePickerImpl datePicker) {
    	UtilDateModel model = (UtilDateModel) datePicker.getModel();
        try {
            LocalDate selectedDate = LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
            return selectedDate;
        } catch (DateTimeException e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return null;
        }
    }
    
    // Method to read the value of startTimeField as LocalTime
    public LocalTime getStartTimeFieldValue() {
        return convertSpinnerToLocalTime(startTimeSpinner);
    }

    // Method to read the value of endTimeField as LocalTime
    public LocalTime getEndTimeFieldValue() {
        return convertSpinnerToLocalTime(endTimeSpinner);
    }
    
    private LocalTime convertSpinnerToLocalTime(JSpinner spinner) {
    	Date selectedDate = (Date) spinner.getValue();
        // Convert the Date to Instant
        Instant instant = selectedDate.toInstant();
        // Create a LocalDateTime using the Instant and the default time zone
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // Extract the LocalTime from the LocalDateTime
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime;
    }
	
	public JButton getSaveButton() {
    	return saveButton;
    }
    
    public JButton getDeleteButton() {
    	return deleteButton;
    }
    
	public Event getEvent() {
    	return event;
    }
}
