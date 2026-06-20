package com.eventbooking.events.dto;

public class UserResponse {
    private final String firstName;
    private final String lastName;
    private final String userName;

    public UserResponse(String firstName, String lastName, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return lastName;
    }



    public String getUserName() {
        return userName;
    }

}
