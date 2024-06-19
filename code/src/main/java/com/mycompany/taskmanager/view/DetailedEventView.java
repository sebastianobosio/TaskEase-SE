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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
	
	private void initializeComponents() {
        titleField = new JTextField();
        descriptionArea = new JTextArea("", 5, 20);
        locationArea = new JTextArea("", 5, 20);
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        
        //date picker
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        UtilDateModel startDateModel = new UtilDateModel();
        UtilDateModel endDateModel = new UtilDateModel();
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, properties);
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, properties);
        startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
        
        //time picker
        SpinnerDateModel startSpinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        SpinnerDateModel endSpinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        startTimeSpinner = new JSpinner(startSpinnerModel);
        endTimeSpinner = new JSpinner(endSpinnerModel);
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);
        endTimeSpinner.setEditor(endTimeEditor);

        // Set up the layout
        JPanel panel = new JPanel(new GridLayout(9, 2));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(new JLabel("Start Date (dd-MM-yyyy):"));
        panel.add(startDatePicker);
        panel.add(new JLabel("Start Time (HH:mm):"));
        panel.add(startTimeSpinner);
        panel.add(new JLabel("End Date (dd-MM-yyyy):"));
        panel.add(endDatePicker);
        panel.add(new JLabel("End Time (HH:mm):"));
        panel.add(endTimeSpinner);
        panel.add(new JLabel("Location"));
        panel.add(locationArea);
        panel.add(saveButton);
        panel.add(deleteButton);
        
        add(panel);
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
