package com.ecommunity.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User JavaBean - Represents a user in the system
 * Follows JavaBean conventions with private fields and public getters/setters
 */
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Private fields
    private int userId;
    private String email;
    private String password;
    private String fullName;
    private String userType; // STUDENT or PUBLIC
    private String matricNumber; // Required for STUDENT
    private String icNumber; // Required for PUBLIC
    private String phoneNumber;
    private Timestamp registrationDate;
    private boolean isDeleted;
    private boolean isAdmin; // NEW: Admin flag
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public User() {
    }
    
    // Parameterized constructor
    public User(String email, String password, String fullName, String userType) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.userType = userType;
    }
    
    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public String getMatricNumber() {
        return matricNumber;
    }
    
    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }
    
    public String getIcNumber() {
        return icNumber;
    }
    
    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public boolean isDeleted() {
        return isDeleted;
    }
    
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
    
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
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
    
    // toString method for debugging
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userType='" + userType + '\'' +
                ", matricNumber='" + matricNumber + '\'' +
                ", icNumber='" + icNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}