package com.mycompany.taskmanager.view;

import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.model.Task;

import javax.swing.*;
import java.awt.*;

public class EventView extends JPanel {
    private JLabel eventTitleLabel;
    private JLabel eventDateLabel;
    private JLabel eventTimeLabel;
    private Event event;
    
    public EventView(Event event) {
    	// Initialize components
    	this.event = event;
        eventTitleLabel = new JLabel(event.getTitle());
        eventDateLabel = new JLabel("Date: " + event.getStartDate() + " to " + event.getEndDate());
        eventTimeLabel = new JLabel("Time: " + event.getStartTime() + " to " + event.getEndTime());

        // Set up layout
        setLayout(new GridLayout(3,1));
        add(eventTitleLabel);
        add(eventDateLabel);
        add(eventTimeLabel);
        
        setPreferredSize(new Dimension(300, 100));
    }
    
    public Event getEvent() {
        return event;
    }
}