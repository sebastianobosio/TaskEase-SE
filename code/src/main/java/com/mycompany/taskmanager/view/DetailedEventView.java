package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;

import com.mycompany.taskmanager.model.Event;

public class DetailedEventView extends JFrame {
	
	public DetailedEventView(Event event) {
        // Set up the frame
        setTitle("Detailed View");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and add components to display detailed task information
        JLabel eventTitleLabel = new JLabel(event.getTitle());
        JLabel eventDescriptionLabel = new JLabel(event.getDescription());
        JLabel eventDateLabel = new JLabel("Date: " + event.getStartDate() + " to " + event.getEndDate());
        JLabel eventTimeLabel = new JLabel("Time: " + event.getStartTime() + " to " + event.getEndTime());
        JLabel eventLocationLabel = new JLabel("Location: " + event.getLocation());

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(eventTitleLabel);
        panel.add(eventDescriptionLabel);
        panel.add(eventDateLabel);
        panel.add(eventTimeLabel);
        panel.add(eventLocationLabel);

        add(panel);
    }
}
