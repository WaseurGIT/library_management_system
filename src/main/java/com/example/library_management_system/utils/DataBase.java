package com.example.library_management_system.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class DataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/library_management_system"; // Change as needed
    private static final String USER = "root"; // Replace with your database username
    private static final String PASSWORD = ""; // Replace with your database password

    // Method to establish a database connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }

    // Validate user by comparing hashed passwords
    public static boolean validUser(String email, String password) {
        Connection connection = getConnection();

        if (connection != null) {
            String query = "SELECT password FROM users WHERE email = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String storedHashedPassword = resultSet.getString("password");

                    // Compare provided password with stored hash
                    if (BCrypt.checkpw(password, storedHashedPassword)) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }

        return false;
    }

    public static Boolean userRegistered(String userName, String regEmail, String regPassword) {
        Connection connection = getConnection();

        if (connection != null) {
            String insertQuery = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            String hashedPassword = BCrypt.hashpw(regPassword, BCrypt.gensalt());

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, userName);
                statement.setString(2, regEmail);
                statement.setString(3, hashedPassword);  // Store hashed password

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;  // Return true if insertion was successful

            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Error: Email already exists.");
            } catch (SQLException e) {
                System.out.println("Error executing insert query: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }

        return false;
    }

    public static Boolean insertBookDetails(String bookName, String bookAuthorName, String bookImage, String bookDept) {

        Connection connection = getConnection();

        if (connection != null) {
            String insertQuery = "INSERT INTO bookDetails (bookName, bookAuthor, bookImage, bookDept) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, bookName);
                statement.setString(2, bookAuthorName);
                statement.setString(3, bookImage);
                statement.setString(4, bookDept);

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;  // Return true if insertion was successful

            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Error: Book with this name already exists.");
            } catch (SQLException e) {
                System.out.println("Error executing insert query: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }

        return false;
    }
}
