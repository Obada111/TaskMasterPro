package com.taskmaster;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDialog extends JDialog {
    private Task task;
    private boolean confirmed = false;
    private TaskManager taskManager;

    // Fields
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> statusCombo;
    private JComboBox<String> priorityCombo;
    private JComboBox<String> categoryCombo;
    private JTextField tagsField;
    private JTextField dueDateField;
    private JComboBox<String> recurringCombo;
    private JCheckBox reminderCheck;

    public TaskDialog(JFrame parent, Task editTask, TaskManager taskManager) {
        super(parent, true);
        this.taskManager = taskManager;
        this.task = editTask != null ? editTask : new Task();

        setTitle(editTask != null ? "Edit Task" : "Add Task");
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setResizable(false);

        initializeUI();
        if (editTask != null) {
            populateFields(editTask);
        }

        setVisible(true);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel fieldsPanel = new JPanel(new GridLayout(9, 2, 10, 10));

        // Title
        fieldsPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        fieldsPanel.add(titleField);

        // Status
        fieldsPanel.add(new JLabel("Status:"));
        statusCombo = new JComboBox<>(new String[]{"TODO", "IN_PROGRESS", "DONE"});
        fieldsPanel.add(statusCombo);

        // Priority
        fieldsPanel.add(new JLabel("Priority:"));
        priorityCombo = new JComboBox<>(new String[]{"HIGH", "MEDIUM", "LOW"});
        fieldsPanel.add(priorityCombo);

        // Category
        fieldsPanel.add(new JLabel("Category:"));
        categoryCombo = new JComboBox<>();
        updateCategoryList();
        fieldsPanel.add(categoryCombo);

        // Tags
        fieldsPanel.add(new JLabel("Tags:"));
        tagsField = new JTextField();
        fieldsPanel.add(tagsField);

        // Due Date
        fieldsPanel.add(new JLabel("Due Date (yyyy-MM-dd):"));
        dueDateField = new JTextField();
        fieldsPanel.add(dueDateField);

        // Recurring
        fieldsPanel.add(new JLabel("Recurring:"));
        recurringCombo = new JComboBox<>(new String[]{"NONE", "DAILY", "WEEKLY", "MONTHLY"});
        fieldsPanel.add(recurringCombo);

        // Reminder
        fieldsPanel.add(new JLabel("Enable Reminder:"));
        reminderCheck = new JCheckBox();
        fieldsPanel.add(reminderCheck);

        // Description
        fieldsPanel.add(new JLabel("Description:"));
        fieldsPanel.add(new JLabel(""));

        JPanel descPanel = new JPanel(new BorderLayout());
        descriptionArea = new JTextArea(5, 40);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        descPanel.setPreferredSize(new Dimension(400, 100));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveTask());
        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        mainPanel.add(fieldsPanel, BorderLayout.NORTH);
        mainPanel.add(descPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateCategoryList() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Work");
        model.addElement("Study");
        model.addElement("Personal");
        model.addElement("Health");
        model.addElement("Other");
        categoryCombo.setModel(model);
    }

    private void populateFields(Task t) {
        titleField.setText(t.getTitle());
        descriptionArea.setText(t.getDescription());
        statusCombo.setSelectedItem(t.getStatus());
        priorityCombo.setSelectedItem(t.getPriority());
        categoryCombo.setSelectedItem(t.getCategory());
        tagsField.setText(t.getTags());
        recurringCombo.setSelectedItem(t.getRecurring());
        reminderCheck.setSelected(t.isReminder());

        if (t.getDueDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dueDateField.setText(sdf.format(t.getDueDate()));
        }
    }

    private void saveTask() {
        if (titleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty!");
            return;
        }

        task.setTitle(titleField.getText());
        task.setDescription(descriptionArea.getText());
        task.setStatus((String) statusCombo.getSelectedItem());
        task.setPriority((String) priorityCombo.getSelectedItem());
        task.setCategory((String) categoryCombo.getSelectedItem());
        task.setTags(tagsField.getText());
        task.setRecurring((String) recurringCombo.getSelectedItem());
        task.setReminder(reminderCheck.isSelected());

        if (!dueDateField.getText().trim().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                task.setDueDate(sdf.parse(dueDateField.getText()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format!");
                return;
            }
        }

        confirmed = true;
        dispose();
    }

    public Task getTask() {
        return task;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}