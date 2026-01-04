package com.ecommunity.dao;

import com.ecommunity.bean.Participation;
import com.ecommunity.util.DBConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ParticipationDAO - Data Access Object for Participation entity
 */
public class ParticipationDAO {
    
    private Connection connection;
    
    public ParticipationDAO() {
        this.connection = DBConnection.getConnection();
    }
    
    /**
     * CREATE - User joins a program
     */
    public boolean createParticipation(Participation participation) {
        String sql = "INSERT INTO participation (user_id, program_id) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, participation.getUserId());
            stmt.setInt(2, participation.getProgramId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating participation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * READ - Get participation by ID
     */
    public Participation getParticipationById(int participationId) {
        String sql = "SELECT * FROM participation WHERE participation_id = ? AND is_deleted = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, participationId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractParticipationFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * READ - Get all participations by user
     */
    public List<Participation> getParticipationsByUser(int userId) {
        List<Participation> participations = new ArrayList<>();
        String sql = "SELECT p.*, vp.program_name " +
                     "FROM participation p " +
                     "JOIN volunteer_program vp ON p.program_id = vp.program_id " +
                     "WHERE p.user_id = ? AND p.is_deleted = 0 " +
                     "ORDER BY p.registration_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Participation p = extractParticipationFromResultSet(rs);
                p.setProgramName(rs.getString("program_name"));
                participations.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participations;
    }
    
    /**
     * UPDATE - Update participation details
     */
    public boolean updateParticipation(Participation participation) {
        String sql = "UPDATE participation SET attendance_status = ?, hours_contributed = ?, " +
                     "feedback = ?, rating = ? WHERE participation_id = ? AND is_deleted = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, participation.getAttendanceStatus());
            stmt.setBigDecimal(2, participation.getHoursContributed());
            stmt.setString(3, participation.getFeedback());
            if (participation.getRating() != null) {
                stmt.setInt(4, participation.getRating());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setInt(5, participation.getParticipationId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * DELETE - Soft delete participation
     */
    public boolean deleteParticipation(int participationId) {
        String sql = "UPDATE participation SET is_deleted = 1 WHERE participation_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, participationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Check if user already joined a program
     */
    public boolean hasUserJoinedProgram(int userId, int programId) {
        String sql = "SELECT COUNT(*) FROM participation " +
                     "WHERE user_id = ? AND program_id = ? AND is_deleted = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, programId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Get total participation count
     */
    public int getTotalParticipationCount() {
        String sql = "SELECT COUNT(*) FROM participation WHERE is_deleted = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Get participation count by user
     */
    public int getParticipationCountByUser(int userId) {
        String sql = "SELECT COUNT(*) FROM participation " +
                     "WHERE user_id = ? AND is_deleted = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Get total hours contributed by user
     */
    public double getTotalHoursByUser(int userId) {
        String sql = "SELECT SUM(hours_contributed) FROM participation " +
                     "WHERE user_id = ? AND is_deleted = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal(1);
                return total != null ? total.doubleValue() : 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    /**
     * Extract Participation from ResultSet
     */
    private Participation extractParticipationFromResultSet(ResultSet rs) throws SQLException {
        Participation p = new Participation();
        p.setParticipationId(rs.getInt("participation_id"));
        p.setUserId(rs.getInt("user_id"));
        p.setProgramId(rs.getInt("program_id"));
        p.setRegistrationDate(rs.getTimestamp("registration_date"));
        p.setAttendanceStatus(rs.getString("attendance_status"));
        p.setHoursContributed(rs.getBigDecimal("hours_contributed"));
        p.setFeedback(rs.getString("feedback"));
        
        int rating = rs.getInt("rating");
        if (!rs.wasNull()) {
            p.setRating(rating);
        }
        
        p.setDeleted(rs.getInt("is_deleted") == 1);
        p.setCreatedAt(rs.getTimestamp("created_at"));
        p.setUpdatedAt(rs.getTimestamp("updated_at"));
        return p;
    }
}