package com.taskmaster;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskMasterPro extends JFrame {
    private TaskManager taskManager;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private JComboBox<String> priorityFilter;
    private JComboBox<String> categoryFilter;
    private JTextArea detailsArea;
    private JLabel statsLabel;
    private Task selectedTask;

    public TaskMasterPro() {
        taskManager = new TaskManager();
        initializeUI();
        loadTasks();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeUI() {
        setTitle("TaskMaster Pro");
        setSize(1200, 700);
        setResizable(true);

        // Top Panel - Search and Add
        JPanel topPanel = createTopPanel();
        
        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Left Panel - Task List
        JPanel leftPanel = createLeftPanel();
        
        // Center Panel - Task Details
        JPanel centerPanel = createCenterPanel();
        
        // Right Panel - Filters and Stats
        JPanel rightPanel = createRightPanel();

        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        // Search field
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                applyFilters();
            }
        });

        // Add task button
        JButton addBtn = new JButton("➕ Add Task");
        addBtn.addActionListener(e -> showAddTaskDialog());
        
        // Add subtask button
        JButton addSubBtn = new JButton("➕ Add Subtask");
        addSubBtn.addActionListener(e -> showAddSubtaskDialog());

        JPanel leftTop = new JPanel(new BorderLayout(5, 0));
        leftTop.setOpaque(false);
        leftTop.add(new JLabel("🔍 Search:"), BorderLayout.WEST);
        leftTop.add(searchField, BorderLayout.CENTER);

        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightTop.setOpaque(false);
        rightTop.add(addBtn);
        rightTop.add(addSubBtn);

        panel.add(leftTop, BorderLayout.CENTER);
        panel.add(rightTop, BorderLayout.EAST);

        return panel;
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setPreferredSize(new Dimension(350, 0));

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setCellRenderer(new TaskListRenderer());
        taskList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedTask = taskList.getSelectedValue();
                updateDetailsPanel();
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(new TitledBorder("📋 Tasks"));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBorder(new TitledBorder("📝 Task Details"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        JButton editBtn = new JButton("✏️ Edit");
        editBtn.addActionListener(e -> showEditTaskDialog());
        
        JButton deleteBtn = new JButton("🗑️ Delete");
        deleteBtn.addActionListener(e -> deleteTask());
        
        JButton markDoneBtn = new JButton("✅ Mark Done");
        markDoneBtn.addActionListener(e -> markTaskDone());
        
        JButton markProgressBtn = new JButton("⏳ In Progress");
        markProgressBtn.addActionListener(e -> markTaskInProgress());

        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(markDoneBtn);
        buttonPanel.add(markProgressBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setPreferredSize(new Dimension(280, 0));

        // Filters
        JPanel filterPanel = createFilterPanel();
        
        // Statistics
        JPanel statsPanel = createStatsPanel();

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(new TitledBorder("🎯 Filters"));

        panel.add(new JLabel("Status:"));
        statusFilter = new JComboBox<>(new String[]{"All", "TODO", "IN_PROGRESS", "DONE"});
        statusFilter.addActionListener(e -> applyFilters());
        panel.add(statusFilter);

        panel.add(new JLabel("Priority:"));
        priorityFilter = new JComboBox<>(new String[]{"All", "HIGH", "MEDIUM", "LOW"});
        priorityFilter.addActionListener(e -> applyFilters());
        panel.add(priorityFilter);

        panel.add(new JLabel("Category:"));
        categoryFilter = new JComboBox<>();
        updateCategoryFilter();
        categoryFilter.addActionListener(e -> applyFilters());
        panel.add(categoryFilter);

        JButton clearFiltersBtn = new JButton("Clear Filters");
        clearFiltersBtn.addActionListener(e -> clearFilters());
        panel.add(clearFiltersBtn);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 10));
        panel.setBorder(new TitledBorder("📊 Statistics"));

        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(statsLabel);

        updateStatistics();

        return panel;
    }

    private void updateStatistics() {
        int total = taskManager.getAllTasks().size();
        int completed = taskManager.getTasksByStatus("DONE").size();
        int pending = total - completed;
        int percentage = total > 0 ? (completed * 100) / total : 0;

        StringBuilder stats = new StringBuilder();
        stats.append("<html>");
        stats.append("Total Tasks: <b>").append(total).append("</b><br>");
        stats.append("Completed: <b>").append(completed).append("</b><br>");
        stats.append("Pending: <b>").append(pending).append("</b><br>");
        stats.append("Progress: <b>").append(percentage).append("%</b><br>");
        stats.append("</html>");

        statsLabel.setText(stats.toString());
    }

    private void updateCategoryFilter() {
        Set<String> categories = new HashSet<>();
        categories.add("All");
        for (Task task : taskManager.getAllTasks()) {
            if (task.getCategory() != null && !task.getCategory().isEmpty()) {
                categories.add(task.getCategory());
            }
        }

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String cat : categories) {
            model.addElement(cat);
        }
        categoryFilter.setModel(model);
    }

    private void applyFilters() {
        String searchText = searchField.getText().toLowerCase();
        String status = (String) statusFilter.getSelectedItem();
        String priority = (String) priorityFilter.getSelectedItem();
        String category = (String) categoryFilter.getSelectedItem();

        var tasks = taskManager.searchAndFilter(searchText, status, priority, category);

        listModel.clear();
        for (Task task : tasks) {
            listModel.addElement(task);
        }
    }

    private void clearFilters() {
        searchField.setText("");
        statusFilter.setSelectedIndex(0);
        priorityFilter.setSelectedIndex(0);
        categoryFilter.setSelectedIndex(0);
        applyFilters();
    }

    private void updateDetailsPanel() {
        if (selectedTask == null) {
            detailsArea.setText("");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder details = new StringBuilder();
        details.append("TASK: ").append(selectedTask.getTitle()).append("\n");
        details.append("─".repeat(50)).append("\n\n");
        details.append("Status: ").append(selectedTask.getStatus()).append("\n");
        details.append("Priority: ").append(selectedTask.getPriority()).append("\n");
        details.append("Category: ").append(selectedTask.getCategory()).append("\n");
        details.append("Tags: ").append(selectedTask.getTags()).append("\n\n");
        
        if (selectedTask.getDueDate() != null) {
            details.append("Due Date: ").append(sdf.format(selectedTask.getDueDate())).append("\n");
        }
        if (!selectedTask.getRecurring().equals("NONE")) {
            details.append("Recurring: ").append(selectedTask.getRecurring()).append("\n");
        }
        details.append("Reminder: ").append(selectedTask.isReminder() ? "Enabled" : "Disabled").append("\n\n");

        details.append("Description:\n");
        details.append(selectedTask.getDescription()).append("\n\n");

        if (!selectedTask.getSubTasks().isEmpty()) {
            details.append("Subtasks:\n");
            for (SubTask sub : selectedTask.getSubTasks()) {
                details.append("  ☐ ").append(sub.getTitle());
                if (sub.isCompleted()) {
                    details.setLength(details.length() - 1);
                    details.append(" ✓");
                }
                details.append("\n");
            }
        }

        detailsArea.setText(details.toString());
    }

    private void showAddTaskDialog() {
        TaskDialog dialog = new TaskDialog(this, null, taskManager);
        if (dialog.isConfirmed()) {
            taskManager.addTask(dialog.getTask());
            taskManager.saveTasks();
            updateCategoryFilter();
            applyFilters();
            updateStatistics();
        }
    }

    private void showEditTaskDialog() {
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Please select a task to edit.");
            return;
        }
        TaskDialog dialog = new TaskDialog(this, selectedTask, taskManager);
        if (dialog.isConfirmed()) {
            taskManager.updateTask(dialog.getTask());
            taskManager.saveTasks();
            updateDetailsPanel();
            applyFilters();
            updateStatistics();
        }
    }

    private void showAddSubtaskDialog() {
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Please select a task to add subtask to.");
            return;
        }
        String title = JOptionPane.showInputDialog(this, "Subtask title:");
        if (title != null && !title.trim().isEmpty()) {
            SubTask subTask = new SubTask(UUID.randomUUID().toString(), title);
            selectedTask.getSubTasks().add(subTask);
            taskManager.saveTasks();
            updateDetailsPanel();
        }
    }

    private void deleteTask() {
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.");
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "Delete this task?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            taskManager.deleteTask(selectedTask.getId());
            taskManager.saveTasks();
            selectedTask = null;
            applyFilters();
            updateStatistics();
        }
    }

    private void markTaskDone() {
        if (selectedTask == null) return;
        selectedTask.setStatus("DONE");
        taskManager.saveTasks();
        updateDetailsPanel();
        applyFilters();
        updateStatistics();
    }

    private void markTaskInProgress() {
        if (selectedTask == null) return;
        selectedTask.setStatus("IN_PROGRESS");
        taskManager.saveTasks();
        updateDetailsPanel();
        applyFilters();
        updateStatistics();
    }

    private void loadTasks() {
        taskManager.loadTasks();
        applyFilters();
        updateStatistics();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskMasterPro());
    }
}