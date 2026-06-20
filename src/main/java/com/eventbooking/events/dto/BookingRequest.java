package com.eventbooking.events.dto;

public class BookingRequest {
    private Long userId;
    private Long eventId;
    private int numberOfTickets;

    public BookingRequest(Long userId, Long eventId, int numberOfTickets) {
        this.userId = userId;
        this.eventId = eventId;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
