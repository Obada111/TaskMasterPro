# TaskMaster Pro 📝

A task management app I built using Java Swing. It helps you organize your tasks with priorities, categories, and deadlines.

---

## 📸 How It Looks

### Main Window
The app has three sections:
- **Left**: Your task list with colors for priorities
- **Middle**: Details about the selected task  
- **Right**: Filters and statistics

<img width="1187" height="692" alt="Screenshot 2026-05-17 220408" src="https://github.com/user-attachments/assets/aaa64522-c021-490d-a1cd-72a76f67d4f4" />
<img width="1187" height="685" alt="Screenshot 2026-05-17 220426" src="https://github.com/user-attachments/assets/13a7e39f-bc91-4bae-8f61-02947f0c3b3d" />


### Adding a Task
When you click "Add Task", a form appears where you can fill in all the details.


---

## ✨ Features

### What Can You Do?

**Manage Tasks**
- Create new tasks
- Edit existing ones
- Delete tasks you don't need
- Mark tasks as Done, In Progress, or Todo
- Add subtasks to break down big tasks

**Organize Your Work**
- Set priority levels (High, Medium, Low) - shown with different colors
- Add categories like Work, Study, Personal
- Add tags like #urgent or #exam
- Set due dates
- Make tasks repeat daily, weekly, or monthly

**Find What You Need**
- Search by task name, description, or tags
- Filter by status, priority, or category
- See all your tasks or just the ones you're working on

**Track Your Progress**
- See how many tasks you've completed
- View statistics: total tasks, done, pending
- See your progress percentage

**Save Your Tasks**
- Everything saves automatically to a file
- Your tasks don't disappear when you close the app
- Simple JSON file format

---

## 🚀 Getting Started

### What You Need
- Java 17 or newer
- NetBeans IDE (easiest way)

### Quick Setup (5 minutes)

**Step 1: Create a new Java project in NetBeans**
- File → New Project
- Choose "Java Application"
- Name it "TaskMasterPro"
- Don't create a main class

**Step 2: Create a package**
- Right-click "Source Packages"
- New → Package
- Name it: `com.taskmaster`

**Step 3: Add the Java files**
- Copy these 7 files into the `com.taskmaster` package:
  - TaskMasterPro.java
  - Task.java
  - SubTask.java
  - TaskManager.java
  - StorageManager.java
  - TaskListRenderer.java
  - TaskDialog.java

**Step 4: Run it**
- Right-click TaskMasterPro.java
- Click "Run File"

That's it! The app should start. ✅

### First Time Using It?
- Click "➕ Add Task" to create a task
- Fill in the details
- Click Save
- Your task appears in the list
- Select a task to see details on the right
- Try the filters on the right panel

---

## 📁 The Code Files

I split the code into 7 files to keep it organized:

| File | What it does |
|------|-------------|
| **TaskMasterPro.java** | The main window - all the GUI stuff |
| **Task.java** | The task object - stores task data |
| **SubTask.java** | For subtasks within tasks |
| **TaskManager.java** | The business logic - makes everything work |
| **StorageManager.java** | Saves and loads tasks from a JSON file |
| **TaskListRenderer.java** | Makes the list look nice with colors |
| **TaskDialog.java** | The popup form for adding/editing tasks |

---

## 📖 How to Use This

### Creating a Task
1. Click "➕ Add Task"
2. Fill in:
   - **Title** (what's the task called?)
   - **Description** (details)
   - **Priority** (High/Medium/Low)
   - **Category** (Work/Study/Personal/etc)
   - **Due Date** (when? format: yyyy-MM-dd)
   - **Tags** (like #exam or #urgent)
   - **Recurring** (does it repeat?)
3. Click Save

### Editing & Deleting
- Click a task to see it
- Click "✏️ Edit" to change it
- Click "🗑️ Delete" to remove it

### Filtering
- Use the search box to find tasks
- Use the dropdowns on the right to filter by status, priority, or category
- Click "Clear Filters" to see everything again

### Subtasks
- Select a task
- Click "➕ Add Subtask"
- Enter the subtask name

---

## 💾 Where's My Data?

Your tasks are saved in a file called `tasks_data.json` in your project folder. 

It's just a text file with JSON format, so you can open it and see your data if you want. Don't edit it though - just let the app handle it.

---

## 📊 Stats

The right panel shows:
- **Total Tasks**: How many tasks you have
- **Completed**: How many you finished
- **Pending**: How many are left
- **Progress**: A percentage of what you've done

These update automatically!

---

## 🎨 Colors Explained

| Color | Meaning |
|-------|---------|
| 🔴 Red | High priority |
| 🟡 Yellow | Medium priority |
| 🟢 Green | Low priority |
| Gray | Task is done |

---

## 🤔 Troubleshooting

### "Application won't start"
- Make sure Java 17+ is installed
- Check that all 7 files are in the `com.taskmaster` package

### "Tasks disappear when I close the app"
- Check if `tasks_data.json` exists in your project folder
- If it does, the file might be corrupted - try deleting it and starting fresh

### "Things are running slow"
- If you have 1000+ tasks, it might be slow
- Try deleting old completed tasks

---

## 🙏 Acknowledgments

This project was created as part of my coursework and I want to thank:

**Dr. Motasem Alheeh** 👨‍🏫 - For teaching me Java and showing me how to build real applications

**Dr. Sabha Abu Sabha** 👩‍🏫 - For helping me understand GUI development and software design

Thank you both for your guidance and support! This project helped me learn so much about Java and software architecture.

---

## 📝 License

MIT License - You can use this code for anything you want. Just keep the license file with it if you share it.

---

## 🎓 What I Learned Making This

- How to use Java Swing for GUI
- How to organize code into packages
- How to save and load data from files
- How to use event listeners and callbacks
- How to build a real application from scratch
- How to write good code and comments

---

## 💡 Things I Could Add Later

- Dark mode theme
- Export tasks to PDF
- Undo/redo feature
- Search by date range
- Weekly calendar view
- Cloud sync
- Mobile app version

---

## 📧 Questions?

If something doesn't work:
1. Check the Troubleshooting section
2. Make sure you followed the setup steps
3. Check the console for error messages
4. Try restarting NetBeans

---

**Made by a student for other students** 👨‍💻

Good luck with your tasks! 📚✨
