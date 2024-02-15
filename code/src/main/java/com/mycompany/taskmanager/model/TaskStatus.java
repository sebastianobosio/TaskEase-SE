package com.mycompany.taskmanager.model;

public enum TaskStatus {
	COMPLETED("Completed"),
	ONGOING("In Progress"),
	NOTSTARTED("Not Started");
	
	private final String label;

	TaskStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}