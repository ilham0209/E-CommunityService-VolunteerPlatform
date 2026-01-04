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

