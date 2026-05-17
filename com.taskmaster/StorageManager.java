package com.taskmaster;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class StorageManager {
    private static final String DATA_FILE = "tasks_data.json";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void saveTasks(List<Task> tasks) {
        try {
            StringBuilder json = new StringBuilder();
            json.append("{\"tasks\":[");

            for (int i = 0; i < tasks.size(); i++) {
                if (i > 0) json.append(",");
                json.append(taskToJson(tasks.get(i)));
            }

            json.append("]}");

            Files.write(Paths.get(DATA_FILE), json.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(Paths.get(DATA_FILE))) {
                return tasks;
            }

            String content = new String(Files.readAllBytes(Paths.get(DATA_FILE)));
            return parseJson(content);
        } catch (IOException e) {
            e.printStackTrace();
            return tasks;
        }
    }

    private String taskToJson(Task task) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"").append(escapeJson(task.getId())).append("\",");
        json.append("\"title\":\"").append(escapeJson(task.getTitle())).append("\",");
        json.append("\"description\":\"").append(escapeJson(task.getDescription())).append("\",");
        json.append("\"status\":\"").append(task.getStatus()).append("\",");
        json.append("\"priority\":\"").append(task.getPriority()).append("\",");
        json.append("\"category\":\"").append(escapeJson(task.getCategory())).append("\",");
        json.append("\"tags\":\"").append(escapeJson(task.getTags())).append("\",");
        json.append("\"dueDate\":").append(task.getDueDate() != null ? "\"" + dateFormat.format(task.getDueDate()) + "\"" : "null").append(",");
        json.append("\"recurring\":\"").append(task.getRecurring()).append("\",");
        json.append("\"reminder\":").append(task.isReminder()).append(",");
        json.append("\"createdDate\":").append(task.getCreatedDate()).append(",");
        json.append("\"subTasks\":[");

        List<SubTask> subTasks = task.getSubTasks();
        for (int i = 0; i < subTasks.size(); i++) {
            if (i > 0) json.append(",");
            json.append(subtaskToJson(subTasks.get(i)));
        }

        json.append("]");
        json.append("}");
        return json.toString();
    }

    private String subtaskToJson(SubTask subTask) {
        return "{\"id\":\"" + escapeJson(subTask.getId()) + "\",\"title\":\"" + 
               escapeJson(subTask.getTitle()) + "\",\"completed\":" + subTask.isCompleted() + "}";
    }

    private List<Task> parseJson(String jsonContent) {
        List<Task> tasks = new ArrayList<>();
        try {
            // Simple JSON parsing
            int startIndex = jsonContent.indexOf("[");
            int endIndex = jsonContent.lastIndexOf("]");
            if (startIndex == -1 || endIndex == -1) return tasks;

            String tasksArray = jsonContent.substring(startIndex + 1, endIndex);
            List<String> taskStrings = splitJsonObjects(tasksArray);

            for (String taskStr : taskStrings) {
                Task task = parseTaskJson(taskStr);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private List<String> splitJsonObjects(String json) {
        List<String> objects = new ArrayList<>();
        int braceCount = 0;
        int startIndex = 0;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                if (braceCount == 0) startIndex = i;
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    objects.add(json.substring(startIndex, i + 1));
                }
            }
        }

        return objects;
    }

    private Task parseTaskJson(String jsonStr) {
        Task task = new Task();
        try {
            task.setId(extractJsonValue(jsonStr, "id"));
            task.setTitle(extractJsonValue(jsonStr, "title"));
            task.setDescription(extractJsonValue(jsonStr, "description"));
            task.setStatus(extractJsonValue(jsonStr, "status"));
            task.setPriority(extractJsonValue(jsonStr, "priority"));
            task.setCategory(extractJsonValue(jsonStr, "category"));
            task.setTags(extractJsonValue(jsonStr, "tags"));
            
            String dueDate = extractJsonValue(jsonStr, "dueDate");
            if (dueDate != null && !dueDate.equals("null")) {
                try {
                    task.setDueDate(dateFormat.parse(dueDate));
                } catch (Exception e) {
                    // Keep null
                }
            }

            task.setRecurring(extractJsonValue(jsonStr, "recurring"));
            task.setReminder(Boolean.parseBoolean(extractJsonValue(jsonStr, "reminder")));
            
            String createdDate = extractJsonValue(jsonStr, "createdDate");
            if (createdDate != null && !createdDate.equals("null")) {
                task.setCreatedDate(Long.parseLong(createdDate));
            }

            // Parse subtasks
            int subTasksStart = jsonStr.indexOf("\"subTasks\":[");
            if (subTasksStart != -1) {
                int arrayStart = jsonStr.indexOf("[", subTasksStart);
                int arrayEnd = jsonStr.indexOf("]", arrayStart);
                String subTasksStr = jsonStr.substring(arrayStart + 1, arrayEnd);
                List<String> subTaskStrs = splitJsonObjects(subTasksStr);

                for (String subStr : subTaskStrs) {
                    if (!subStr.trim().isEmpty()) {
                        String subId = extractJsonValue(subStr, "id");
                        String subTitle = extractJsonValue(subStr, "title");
                        boolean completed = Boolean.parseBoolean(extractJsonValue(subStr, "completed"));
                        SubTask subTask = new SubTask(subId, subTitle);
                        subTask.setCompleted(completed);
                        task.getSubTasks().add(subTask);
                    }
                }
            }

            return task;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return null;

        startIndex += searchKey.length();
        
        // Skip whitespace
        while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
            startIndex++;
        }

        if (json.charAt(startIndex) == '"') {
            startIndex++;
            int endIndex = json.indexOf('"', startIndex);
            while (endIndex < json.length() - 1 && json.charAt(endIndex - 1) == '\\') {
                endIndex = json.indexOf('"', endIndex + 1);
            }
            String value = json.substring(startIndex, endIndex);
            return unescapeJson(value);
        } else {
            int endIndex = startIndex;
            while (endIndex < json.length() && json.charAt(endIndex) != ',' && json.charAt(endIndex) != '}' && json.charAt(endIndex) != ']') {
                endIndex++;
            }
            return json.substring(startIndex, endIndex).trim();
        }
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String unescapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }
}