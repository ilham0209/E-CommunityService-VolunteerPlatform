package com.ecommunity.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ActivityLog JavaBean - Represents system activity logs
 */
public class ActivityLog implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int logId;
    private Integer userId;
    private String actionType;
    private String description;
    private String ipAddress;
    private boolean isDeleted;
    private Timestamp createdAt;
    
    // Constructors
    public ActivityLog() {
    }
    
    public ActivityLog(Integer userId, String actionType, String description, String ipAddress) {
        this.userId = userId;
        this.actionType = actionType;
        this.description = description;
        this.ipAddress = ipAddress;
    }
    
    // Getters and Setters
    public int getLogId() {
        return logId;
    }
    
    public void setLogId(int logId) {
        this.logId = logId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getActionType() {
        return actionType;
    }
    
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
    
    @Override
    public String toString() {
        return "ActivityLog{" +
                "logId=" + logId +
                ", userId=" + userId +
                ", actionType='" + actionType + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}