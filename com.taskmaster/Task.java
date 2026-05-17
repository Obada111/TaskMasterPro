package com.taskmaster;

import java.io.Serializable;
import java.util.*;

public class Task implements Serializable {
    private String id;
    private String title;
    private String description;
    private String status; // TODO, IN_PROGRESS, DONE
    private String priority; // HIGH, MEDIUM, LOW
    private String category;
    private String tags;
    private Date dueDate;
    private String recurring; // NONE, DAILY, WEEKLY, MONTHLY
    private boolean reminder;
    private List<SubTask> subTasks;
    private long createdDate;

    public Task() {
        this.id = UUID.randomUUID().toString();
        this.status = "TODO";
        this.priority = "MEDIUM";
        this.category = "";
        this.tags = "";
        this.recurring = "NONE";
        this.reminder = false;
        this.subTasks = new ArrayList<>();
        this.createdDate = System.currentTimeMillis();
    }

    public Task(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public String getRecurring() { return recurring; }
    public void setRecurring(String recurring) { this.recurring = recurring; }

    public boolean isReminder() { return reminder; }
    public void setReminder(boolean reminder) { this.reminder = reminder; }

    public List<SubTask> getSubTasks() { return subTasks; }
    public void setSubTasks(List<SubTask> subTasks) { this.subTasks = subTasks; }

    public long getCreatedDate() { return createdDate; }
    public void setCreatedDate(long createdDate) { this.createdDate = createdDate; }

    @Override
    public String toString() {
        String statusIcon = switch (status) {
            case "DONE" -> "✓";
            case "IN_PROGRESS" -> "◐";
            default -> "○";
        };
        
        String priorityIcon = switch (priority) {
            case "HIGH" -> "🔴";
            case "MEDIUM" -> "🟡";
            case "LOW" -> "🟢";
            default -> "";
        };

        return priorityIcon + " " + statusIcon + " " + title;
    }
}