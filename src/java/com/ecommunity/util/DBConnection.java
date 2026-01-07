package com.ecommunity.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection
 * Utility class to manage Java DB (Apache Derby) connection
 * Compatible with NetBeans 8.2
 */
public class DBConnection {

    // ===============================
    // DATABASE CONFIGURATION
    // ===============================
    private static final String DB_URL =
            "jdbc:derby://localhost:1527/ecommunity_db";

    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "swizard";

    private static Connection connection;

    // ===============================
    // PREVENT INSTANTIATION
    // ===============================
    private DBConnection() {
        // private constructor
    }

    // ===============================
    // GET CONNECTION
    // ===============================
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                // Load Derby Client Driver
                Class.forName("org.apache.derby.jdbc.ClientDriver");

                // Create database connection
                connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD);

                System.out.println("✅ Connected to Java DB (Derby)");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Derby JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to database");
            e.printStackTrace();
        }
        return connection;
    }

    // ===============================
    // CLOSE CONNECTION
    // ===============================
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔌 Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing database connection");
            e.printStackTrace();
        }
    }

    // ===============================
    // TEST CONNECTION
    // ===============================
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
