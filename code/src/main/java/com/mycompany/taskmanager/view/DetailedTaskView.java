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
import java.time.format.DateTimeFormatter;
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
    private JSpinner timeSpinner;
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
        statusComboBox = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        
        //due date picker
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        dueDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        
        //due time picker
        SpinnerDateModel spinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        timeSpinner = new JSpinner(spinnerModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
               
        // Set up the layout
        JPanel panel = new JPanel(new GridLayout(6,2));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(new JLabel("Due Date (dd-MM-yyyy):"));
        panel.add(dueDatePicker);
        panel.add(new JLabel("Due Time (HH:mm):"));
        panel.add(timeSpinner);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(saveButton);
        panel.add(deleteButton);
        
        add(panel);
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
            timeSpinner.setValue(selectedTime);
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
    	Date selectedDate = (Date) timeSpinner.getValue();
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
