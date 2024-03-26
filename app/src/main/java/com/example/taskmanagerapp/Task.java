package com.example.taskmanagerapp;

public class Task{
    private long id; // Add a field to store the task ID
    private String title;
    private String description;
    private String dueDate;

    public Task(long id, String title, String description, String dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Getter method for retrieving the task ID
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nDescription: " + description + "\nDue Date: " + dueDate;
    }
}
