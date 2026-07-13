package com.eventbooking.events.dto;

public class UserResponse {
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final Long id;

    public UserResponse(Long id, String firstName, String lastName, String userName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }


    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }



    public String getUserName() {
        return userName;
    }

}
