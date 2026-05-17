package com.taskmaster;

import javax.swing.*;
import java.awt.*;

public class TaskListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Task) {
            Task task = (Task) value;
            
            // Set text
            setText(task.toString());
            
            // Set font
            setFont(new Font("Arial", Font.PLAIN, 12));
            
            // Set colors based on priority
            if (!isSelected) {
                Color bgColor = switch (task.getPriority()) {
                    case "HIGH" -> new Color(255, 220, 220);
                    case "MEDIUM" -> new Color(255, 250, 200);
                    case "LOW" -> new Color(220, 240, 220);
                    default -> Color.WHITE;
                };
                
                // If task is done, use gray
                if (task.getStatus().equals("DONE")) {
                    bgColor = new Color(240, 240, 240);
                    setForeground(Color.GRAY);
                }
                
                setBackground(bgColor);
            } else {
                setBackground(new Color(100, 150, 255));
                setForeground(Color.WHITE);
            }
            
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        return this;
    }
}