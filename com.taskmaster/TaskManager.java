package com.taskmaster;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;
    private StorageManager storage;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.storage = new StorageManager();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void updateTask(Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(updatedTask.getId())) {
                tasks.set(i, updatedTask);
                return;
            }
        }
    }

    public void deleteTask(String taskId) {
        tasks.removeIf(task -> task.getId().equals(taskId));
    }

    public Task getTaskById(String taskId) {
        return tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public List<Task> getTasksByStatus(String status) {
        return tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksByPriority(String priority) {
        return tasks.stream()
                .filter(task -> task.getPriority().equals(priority))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksByCategory(String category) {
        return tasks.stream()
                .filter(task -> task.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<Task> searchAndFilter(String searchText, String status, String priority, String category) {
        return tasks.stream()
                .filter(task -> {
                    boolean matchesSearch = searchText.isEmpty() ||
                            task.getTitle().toLowerCase().contains(searchText) ||
                            task.getDescription().toLowerCase().contains(searchText) ||
                            task.getTags().toLowerCase().contains(searchText);

                    boolean matchesStatus = status.equals("All") || task.getStatus().equals(status);
                    boolean matchesPriority = priority.equals("All") || task.getPriority().equals(priority);
                    boolean matchesCategory = category.equals("All") || task.getCategory().equals(category);

                    return matchesSearch && matchesStatus && matchesPriority && matchesCategory;
                })
                .collect(Collectors.toList());
    }

    public void saveTasks() {
        storage.saveTasks(tasks);
    }

    public void loadTasks() {
        this.tasks = storage.loadTasks();
    }

    public int getTotalTasks() {
        return tasks.size();
    }

    public int getCompletedTasks() {
        return (int) tasks.stream().filter(t -> t.getStatus().equals("DONE")).count();
    }

    public int getPendingTasks() {
        return getTotalTasks() - getCompletedTasks();
    }

    public int getProductivityPercentage() {
        if (tasks.isEmpty()) return 0;
        return (getCompletedTasks() * 100) / getTotalTasks();
    }
}