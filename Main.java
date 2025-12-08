package com.israel.todolist;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // This ensures the GUI runs on the proper thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoListGUI();
            }
        });
    }
}