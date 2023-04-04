package com.techsophy.awgment.dto;

public class UserRequestPayload {
    private UserDataPayload userData;
    private String realmId;

    public UserRequestPayload(UserDataPayload userData, String realmId) {
        this.userData = userData;
        this.realmId = realmId;
    }

    public UserDataPayload getUserData() {
        return userData;
    }

    public String getRealmId() {
        return realmId;
    }
}
