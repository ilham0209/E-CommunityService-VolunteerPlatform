// ==================== VolunteerProgram.java (JavaBean) ====================
package com.ecommunity.bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class VolunteerProgram implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int programId;
    private String programName;
    private String description;
    private String location;
    private Date startDate;
    private Date endDate;
    private int maxParticipants;
    private int currentParticipants;
    private String category;
    private int organizerId;
    private String status;
    private boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Constructors
    public VolunteerProgram() {}
    
    public VolunteerProgram(String programName, String description, String location, 
                           Date startDate, Date endDate, int maxParticipants, 
                           String category, int organizerId) {
        this.programName = programName;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipants = maxParticipants;
        this.category = category;
        this.organizerId = organizerId;
    }
    
    // Getters and Setters
    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }
    
    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    
    public int getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
    
    public int getCurrentParticipants() { return currentParticipants; }
    public void setCurrentParticipants(int currentParticipants) { this.currentParticipants = currentParticipants; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public int getOrganizerId() { return organizerId; }
    public void setOrganizerId(int organizerId) { this.organizerId = organizerId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}

// ==================== VolunteerProgramDAO.java ====================
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
                     "category = ?, status = ? WHERE program_id = ? AND is_deleted = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, program.getProgramName());
            stmt.setString(2, program.getDescription());
            stmt.setString(3, program.getLocation());
            stmt.setDate(4, program.getStartDate());
            stmt.setDate(5, program.getEndDate());
            stmt.setInt(6, program.getMaxParticipants());
            stmt.setString(7, program.getCategory());
            stmt.setString(8, program.getStatus());
            stmt.setInt(9, program.getProgramId());
            
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