package com.mycompany.taskmanager.view;

import com.mycompany.taskmanager.model.Event;

import javax.swing.*;
import java.awt.*;

public class EventView extends JPanel {
    private JLabel eventTitleLabel;
    private JLabel eventStartDateLabel;
    private JLabel eventEndDateLabel;
    private Event event;
    
    public EventView(Event event) {
    	// Initialize components
    	this.event = event;
        eventTitleLabel = new JLabel(event.getTitle());
        eventStartDateLabel = new JLabel("Inizio: " + event.getFormattedStartDate() + " alle " + event.getFormattedStartTime());
        eventEndDateLabel = new JLabel("Fine: " + event.getFormattedEndDate() + " alle " + event.getFormattedEndTime());

        // Set up layout
        setLayout(new BorderLayout());
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(eventTitleLabel);
        infoPanel.add(eventStartDateLabel);
        infoPanel.add(eventEndDateLabel);
        infoPanel.setPreferredSize(new Dimension(100, 100));
        add(infoPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(400, 100));
    }
    
    public JLabel getEventTitle() {
        return eventTitleLabel;
    }
    
    public Event getEvent() {
    	return event;
    }
}