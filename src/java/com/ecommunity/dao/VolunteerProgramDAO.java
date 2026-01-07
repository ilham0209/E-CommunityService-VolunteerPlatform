package com.ecommunity.dao;

import com.ecommunity.bean.VolunteerProgram;
import com.ecommunity.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Module 3: CRUD Operations for Volunteer Programs with Soft Delete
 */
public class VolunteerProgramDAO {
    
    private Connection connection;
    
    public VolunteerProgramDAO() {
        this.connection = DBConnection.getConnection();
    }
    
    /**
     * CREATE - Insert new volunteer program
     */
    public boolean createProgram(VolunteerProgram program) {
        String sql = "INSERT INTO volunteer_program (program_name, description, location, " +
                     "start_date, end_date, max_participants, category, organizer_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, program.getProgramName());
            stmt.setString(2, program.getDescription());
            stmt.setString(3, program.getLocation());
            stmt.setDate(4, program.getStartDate());
            stmt.setDate(5, program.getEndDate());
            stmt.setInt(6, program.getMaxParticipants());
            stmt.setString(7, program.getCategory());
            stmt.setInt(8, program.getOrganizerId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * READ - Get program by ID (only non-deleted)
     */
    public VolunteerProgram getProgramById(int programId) {
        String sql = "SELECT * FROM volunteer_program WHERE program_id = ? AND is_deleted = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, programId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractProgramFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * READ - Get all active programs
     */
    public List<VolunteerProgram> getAllPrograms() {
        List<VolunteerProgram> programs = new ArrayList<>();
        String sql = "SELECT * FROM volunteer_program WHERE is_deleted = 0 ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                programs.add(extractProgramFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }
    
    /**
     * UPDATE - Modify existing program
     */
    public boolean updateProgram(VolunteerProgram program) {
        String sql = "UPDATE volunteer_program SET program_name = ?, description = ?, " +
                     "location = ?, start_date = ?, end_date = ?, max_participants = ?, " +
                     "category = ?, status = ?, current_participants = ? WHERE program_id = ? AND is_deleted = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, program.getProgramName());
            stmt.setString(2, program.getDescription());
            stmt.setString(3, program.getLocation());
            stmt.setDate(4, program.getStartDate());
            stmt.setDate(5, program.getEndDate());
            stmt.setInt(6, program.getMaxParticipants());
            stmt.setString(7, program.getCategory());
            stmt.setString(8, program.getStatus());
            stmt.setInt(9, program.getCurrentParticipants()); // ADD THIS
            stmt.setInt(10, program.getProgramId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * DELETE - Soft delete (set is_deleted = 1)
     * CRITICAL: This is SOFT DELETE, not physical deletion
     */
    public boolean deleteProgram(int programId) {
        String sql = "UPDATE volunteer_program SET is_deleted = 1 WHERE program_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, programId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Additional helper methods for Dashboard
    public int getTotalProgramCount() {
        String sql = "SELECT COUNT(*) FROM volunteer_program WHERE is_deleted = 0";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getUpcomingProgramCount() {
        String sql = "SELECT COUNT(*) FROM volunteer_program " +
                     "WHERE is_deleted = 0 AND status = 'UPCOMING'";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<VolunteerProgram> getRecentPrograms(int limit) {
        List<VolunteerProgram> programs = new ArrayList<>();
        String sql = "SELECT * FROM volunteer_program WHERE is_deleted = 0 " +
                     "ORDER BY created_at DESC LIMIT ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                programs.add(extractProgramFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }
    
    private VolunteerProgram extractProgramFromResultSet(ResultSet rs) throws SQLException {
        VolunteerProgram program = new VolunteerProgram();
        program.setProgramId(rs.getInt("program_id"));
        program.setProgramName(rs.getString("program_name"));
        program.setDescription(rs.getString("description"));
        program.setLocation(rs.getString("location"));
        program.setStartDate(rs.getDate("start_date"));
        program.setEndDate(rs.getDate("end_date"));
        program.setMaxParticipants(rs.getInt("max_participants"));
        program.setCurrentParticipants(rs.getInt("current_participants"));
        program.setCategory(rs.getString("category"));
        program.setOrganizerId(rs.getInt("organizer_id"));
        program.setStatus(rs.getString("status"));
        program.setDeleted(rs.getBoolean("is_deleted"));
        program.setCreatedAt(rs.getTimestamp("created_at"));
        program.setUpdatedAt(rs.getTimestamp("updated_at"));
        return program;
    }
}