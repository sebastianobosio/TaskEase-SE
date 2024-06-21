package com.mycompany.taskmanager.view;

import com.mycompany.taskmanager.model.Event;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

        // customize label
        eventTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        eventStartDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        eventEndDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        eventTitleLabel.setForeground(new Color(50, 50, 50));
        eventStartDateLabel.setForeground(new Color(80, 80, 80));
        eventEndDateLabel.setForeground(new Color(80, 80, 80));
        
        // Set up layout   
        setLayout(new BorderLayout());
        // Create and style the info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(new Color(144, 238, 144));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        
        infoPanel.add(eventTitleLabel);
        infoPanel.add(eventStartDateLabel);
        infoPanel.add(eventEndDateLabel);

        add(infoPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(400, 100));
        setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
    }
    
    public JLabel getEventTitle() {
        return eventTitleLabel;
    }
    
    public Event getEvent() {
    	return event;
    }
}