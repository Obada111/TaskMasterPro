# TaskMaster Pro

A Java Swing desktop application for task management with priorities, categories, subtasks, filtering, and local JSON storage.

Built as a learning project to practice Java GUI development, OOP design, and file-based persistence.

## Features

- Add, edit, and delete tasks
- Set priority (High, Medium, Low) and status (TODO, In Progress, Done)
- Organize tasks into categories (Work, Study, Personal, Health, Other)
- Add subtasks to any task
- Search tasks by title, description, or tags
- Filter by status, priority, or category
- View task statistics (total, completed, pending, progress)
- Color-coded task list based on priority
- Due date, recurring schedule, and reminder settings
- Persistent storage in JSON format

## Technologies Used

- Java
- Swing (GUI)
- JSON file-based storage
- OOP principles (encapsulation, inheritance, interfaces)

## Installation

### Prerequisites

- Java JDK 11 or higher
- Java compiler (javac)

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/Obada111/TaskMasterPro.git
   cd TaskMasterPro
   ```

2. Compile the project:
   ```bash
   javac com/taskmaster/*.java
   ```

3. Run the application:
   ```bash
   java com.taskmaster.TaskMasterPro
   ```

## Usage

Launch the application to open the task management window.

- **Add a task**: Click "Add Task" and fill in the details
- **Edit a task**: Select a task and click "Edit"
- **Delete a task**: Select a task and click "Delete"
- **Mark as done**: Select a task and click "Mark Done"
- **Filter tasks**: Use the search bar and filter dropdowns on the right
- **View details**: Click a task to see full details and subtasks

## Project Structure

```
TaskMasterPro/
  com.taskmaster/
    TaskMasterPro.java    -- Main application window (JFrame)
    TaskManager.java      -- Business logic and task management
    Task.java             -- Task model class
    SubTask.java          -- SubTask model class
    TaskDialog.java       -- Add/Edit task dialog
    TaskListRenderer.java -- Custom list cell renderer
    StorageManager.java   -- JSON file persistence
  .gitignore
  LICENSE
  README.md
```

## Future Improvements

- Add drag-and-drop task reordering
- Implement data backup and restore
- Add keyboard shortcuts for common actions
- Support for dark mode
- Export tasks to CSV or PDF
