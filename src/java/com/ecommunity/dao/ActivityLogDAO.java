package com.ecommunity.dao;

import com.ecommunity.bean.ActivityLog;
import com.ecommunity.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ActivityLogDAO - Data Access Object for Activity Logs
 */
public class ActivityLogDAO {
    
    private Connection connection;
    
    public ActivityLogDAO() {
        this.connection = DBConnection.getConnection();
    }
    
    /**
     * Log user activity
     */
    public boolean logActivity(Integer userId, String actionType, String description, String ipAddress) {
        String sql = "INSERT INTO activity_log (user_id, action_type, description, ip_address) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (userId != null) {
                stmt.setInt(1, userId);
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setString(2, actionType);
            stmt.setString(3, description);
            stmt.setString(4, ipAddress);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error logging activity: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get activity logs by user
     */
    public List<ActivityLog> getLogsByUser(int userId) {
        List<ActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM activity_log WHERE user_id = ? AND is_deleted = 0 " +
                     "ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                logs.add(extractLogFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
    
    /**
     * Get recent activity logs
     */
    public List<ActivityLog> getRecentLogs(int limit) {
        List<ActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM activity_log WHERE is_deleted = 0 " +
                     "ORDER BY created_at DESC FETCH FIRST ? ROWS ONLY";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                logs.add(extractLogFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
    
    /**
     * Get all activity logs
     */
    public List<ActivityLog> getAllLogs() {
        List<ActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM activity_log WHERE is_deleted = 0 ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                logs.add(extractLogFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
    
    /**
     * Soft delete log
     */
    public boolean deleteLog(int logId) {
        String sql = "UPDATE activity_log SET is_deleted = 1 WHERE log_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, logId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Extract ActivityLog from ResultSet
     */
    private ActivityLog extractLogFromResultSet(ResultSet rs) throws SQLException {
        ActivityLog log = new ActivityLog();
        log.setLogId(rs.getInt("log_id"));
        
        int userId = rs.getInt("user_id");
        if (!rs.wasNull()) {
            log.setUserId(userId);
        }
        
        log.setActionType(rs.getString("action_type"));
        log.setDescription(rs.getString("description"));
        log.setIpAddress(rs.getString("ip_address"));
        log.setDeleted(rs.getInt("is_deleted") == 1);
        log.setCreatedAt(rs.getTimestamp("created_at"));
        return log;
    }
}