package com.techsophy.awgment.dto;

public class UserRequestPayload {
    private String userName;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String emailId;
    private String department;
    private String userId;

    public UserRequestPayload(String userName, String firstName, String lastName, String mobileNumber, String emailId, String department, String userId) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.department = department;
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getDepartment() {
        return department;
    }

    public String getUserId() {
        return userId;
    }
}
