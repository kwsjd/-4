package com.tamik.database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class main {
    static functions db = new functions();

    public static void OpenSecondWindow(Connection conn, String username, String password){
        JFrame CreateOrDropDB = new JFrame("Создать или удалить БД");
        CreateOrDropDB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CreateOrDropDB.setSize(1000, 1000);
        JPanel createAndDropDBPanel = new JPanel();
        JLabel databaseLabel = new JLabel("Название БД");
        JTextField databaseTextField = new JTextField(20);
        createAndDropDBPanel.add(databaseLabel);
        createAndDropDBPanel.add(databaseTextField);
        JButton createDBButton = new JButton("Создать");
        JButton dropDBButton = new JButton("Удалить");
        JButton openDBButton = new JButton("Открыть");
        createAndDropDBPanel.add(createDBButton);
        createAndDropDBPanel.add(openDBButton);
        createAndDropDBPanel.add(dropDBButton);

        createDBButton.addActionListener(e -> {
            try {
                String databaseName = databaseTextField.getText();
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE DATABASE " + databaseName);
                JOptionPane.showMessageDialog(null, "БД создана");
            } catch (Exception connectionException) {
                JOptionPane.showMessageDialog(null, connectionException);
            }
        });

        dropDBButton.addActionListener(e -> {
            try {
                String databaseName = databaseTextField.getText();
                Statement stm = conn.createStatement();
                stm.executeUpdate("DROP DATABASE " + databaseName);
                JOptionPane.showMessageDialog(null, "БД удалена!");
            } catch (Exception connectionException) {
                JOptionPane.showMessageDialog(null, connectionException);
            }
        });

        openDBButton.addActionListener(e -> {
            try {
                String databaseName = databaseTextField.getText();
                Connection openDatabaseConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName, username, password);
                db.createTableFunction(openDatabaseConnection);
                CallableStatement callableStatement = openDatabaseConnection.prepareCall("SELECT createTable()");
                callableStatement.executeQuery();
                JOptionPane.showMessageDialog(null, "Подключен к " + databaseName + " !");
                table_ui dialog = new table_ui(openDatabaseConnection);
                dialog.pack();
                dialog.setVisible(true);
            } catch (Exception connectionException) {
                JOptionPane.showMessageDialog(null, connectionException);
            }
        });

        CreateOrDropDB.add(createAndDropDBPanel);
        CreateOrDropDB.setVisible(true);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame authorizationFrame = new JFrame("Авторизация");
        JPanel usernamePanel = new JPanel();
        JLabel usernameLabel = new JLabel("Пользователь");
        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Пароль");
        JPanel buttonPanel = new JPanel();
        JButton connectButton = new JButton("Далее");

        JTextField usernameTextField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        authorizationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        authorizationFrame.setSize(1000, 1000);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        buttonPanel.add(connectButton);
        authorizationFrame.setLayout(new GridLayout(6, 1));
        authorizationFrame.add(usernamePanel);
        authorizationFrame.add(passwordPanel);
        authorizationFrame.add(buttonPanel);
        authorizationFrame.setVisible(true);

        // Авторизация и подключение к БД
        connectButton.addActionListener(e -> {
            try {
                String username = usernameTextField.getText();
                String password = String.valueOf(passwordField.getPassword());
                Connection conn = null;
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + "postgres", username, password);
                JOptionPane.showMessageDialog(null, "Успешное подключение");
                OpenSecondWindow(conn, username, password);

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception);
            }
        });
    }
}