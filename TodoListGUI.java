package com.israel.todolist;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TodoListGUI extends JFrame {
    private List<Task> tasks;
    private int nextId;

    // GUI Components
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskField;
    private JButton addButton;
    private JButton completeButton;
    private JButton deleteButton;

    public TodoListGUI() {
        tasks = new ArrayList<>();
        nextId = 1;

        setupGUI();
        setTitle("My Todo List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void setupGUI() {
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Title
        JLabel titleLabel = new JLabel("My Todo List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Task list in the center
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input panel at the bottom
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));

        taskField = new JTextField();
        taskField.setFont(new Font("Arial", Font.PLAIN, 14));

        addButton = new JButton("Add Task");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));

        inputPanel.add(new JLabel("New Task:"), BorderLayout.NORTH);
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        completeButton = new JButton("Mark Complete");
        completeButton.setBackground(new Color(34, 139, 34));
        completeButton.setForeground(Color.WHITE);
        completeButton.setFont(new Font("Arial", Font.BOLD, 12));

        deleteButton = new JButton("Delete Task");
        deleteButton.setBackground(new Color(178, 34, 34));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));

        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        // Combine everything
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.add(inputPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Add action listeners
        setupEventListeners();

        add(mainPanel);
    }

    private void setupEventListeners() {
        // Add task when button is clicked or Enter is pressed
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        taskField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        // Mark task as completed
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markTaskCompleted();
            }
        });

        // Delete selected task
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });
    }

    private void addTask() {
        String description = taskField.getText().trim();
        if (!description.isEmpty()) {
            Task newTask = new Task(nextId++, description);
            tasks.add(newTask);
            updateTaskList();
            taskField.setText("");
            taskField.requestFocus(); // Keep focus on text field
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please enter a task description!",
                    "Empty Task",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void markTaskCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task task = tasks.get(selectedIndex);
            task.setCompleted(true);
            updateTaskList();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a task to mark as completed!",
                    "No Task Selected",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            updateTaskList();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a task to delete!",
                    "No Task Selected",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateTaskList() {
        listModel.clear();
        for (Task task : tasks) {
            listModel.addElement(task.toString());
        }
    }
}