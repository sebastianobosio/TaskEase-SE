package com.mycompany.taskmanager.view;

public enum ViewOption {
    TASKS("Tasks"),
    EVENTS("Events"),
    BOTH("Both");

    private final String label;

    ViewOption(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
