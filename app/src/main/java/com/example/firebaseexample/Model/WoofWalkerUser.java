package com.example.firebaseexample.Model;

public class WoofWalkerUser {

    String userEmail;
    String userFirstName;
    String userLastName;

    public WoofWalkerUser() {}

    public WoofWalkerUser(String userEmail, String userFirstName, String userLastName) {
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }
}
