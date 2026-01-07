package com.ecommunity.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Participation JavaBean - Represents user participation in volunteer programs
 */
public class Participation implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int participationId;
    private int userId;
    private int programId;
    private Timestamp registrationDate;
    private String attendanceStatus; // REGISTERED, ATTENDED, ABSENT, CANCELLED
    private BigDecimal hoursContributed;
    private String feedback;
    private Integer rating; // 1-5
    private boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Additional fields for display (JOIN queries)
    private String userName;
    private String programName;
    
    // Constructors
    public Participation() {
    }
    
    public Participation(int userId, int programId) {
        this.userId = userId;
        this.programId = programId;
    }
    
    // Getters and Setters
    public int getParticipationId() {
        return participationId;
    }
    
    public void setParticipationId(int participationId) {
        this.participationId = participationId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getProgramId() {
        return programId;
    }
    
    public void setProgramId(int programId) {
        this.programId = programId;
    }
    
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public String getAttendanceStatus() {
        return attendanceStatus;
    }
    
    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
    
    public BigDecimal getHoursContributed() {
        return hoursContributed;
    }
    
    public void setHoursContributed(BigDecimal hoursContributed) {
        this.hoursContributed = hoursContributed;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public boolean isDeleted() {
        return isDeleted;
    }
    
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getProgramName() {
        return programName;
    }
    
    public void setProgramName(String programName) {
        this.programName = programName;
    }
    
    @Override
    public String toString() {
        return "Participation{" +
                "participationId=" + participationId +
                ", userId=" + userId +
                ", programId=" + programId +
                ", attendanceStatus='" + attendanceStatus + '\'' +
                ", hoursContributed=" + hoursContributed +
                ", rating=" + rating +
                '}';
    }
}