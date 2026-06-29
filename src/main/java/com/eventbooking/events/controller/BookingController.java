package com.eventbooking.events.controller;


import com.eventbooking.events.dto.BookingRequest;
import com.eventbooking.events.model.Booking;
import com.eventbooking.events.service.BookingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest request){
        return bookingService.createBooking(
                request.getUserId(),
                request.getEventId(),
                request.getNumberOfTickets()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        bookingService.deleteBooking(id, userDetails.getUsername());
    }

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }
}
