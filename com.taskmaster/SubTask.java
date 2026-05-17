package com.taskmaster;

import java.io.Serializable;

public class SubTask implements Serializable {
    private String id;
    private String title;
    private boolean completed;

    public SubTask(String id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}