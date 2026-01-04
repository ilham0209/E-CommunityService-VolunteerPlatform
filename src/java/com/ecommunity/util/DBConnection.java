package com.ecommunity.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility for Java DB (Derby) - Singleton Pattern
 */
public class DBConnection {
    
    // Java DB (Derby) connection settings
    private static final String DB_URL = "jdbc:derby://localhost:1527/E-Community Platform";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "swizard";
    private static Connection connection = null;
    
    // Private constructor to prevent instantiation
    private DBConnection() {
    }
    
    /**
     * Get database connection using Singleton pattern
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load Derby JDBC Driver
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                
                // Create connection
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Java DB (Derby) connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Derby JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
        return connection;
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection!");
            e.printStackTrace();
        }
    }
    
    /**
     * Test database connection
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}