package com.techsophy.awgment.dto;

public class UserDataPayload {
    private String userName;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String emailId;
    private String department;
    private String signature;
    private String realmId;

    public UserDataPayload(String userName, String firstName, String lastName, String mobileNumber, String emailId, String department, String signature,String realmId) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.department = department;
        this.signature = signature;
        this.realmId = realmId;
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

    public String getSignature() {
        return signature;
    }
    public String getRealmId() {
        return realmId;
    }
}
