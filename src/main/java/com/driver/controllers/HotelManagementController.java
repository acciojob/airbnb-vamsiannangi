package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.service.HotelManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelManagementController {

    private final HotelManagementService hotelManagementService;

    @Autowired
    public HotelManagementController(HotelManagementService hotelManagementService) {
        this.hotelManagementService = hotelManagementService;
    }

    @PostMapping("/add-hotel")
    public String addHotel(@RequestBody Hotel hotel) {
        return hotelManagementService.addHotel(hotel);
    }

    @PostMapping("/add-user")
    public Integer addUser(@RequestBody User user) {
        return hotelManagementService.addUser(user);
    }

    @GetMapping("/get-hotel-with-most-facilities")
    public String getHotelWithMostFacilities() {
        return hotelManagementService.getHotelWithMostFacilities();
    }

    @PostMapping("/book-a-room")
    public int bookARoom(@RequestBody Booking booking) {
        return hotelManagementService.bookARoom(booking);
    }

    @GetMapping("/get-bookings-by-a-person/{aadharCard}")
    public List<Booking> getBookings(@PathVariable("aadharCard") Integer aadharCard) {
        return hotelManagementService.getBookings(aadharCard);
    }

    @PutMapping("/update-facilities")
    public Hotel updateFacilities(@RequestBody List<Facility> newFacilities, @RequestParam String hotelName) {
        return hotelManagementService.updateFacilities(newFacilities, hotelName);
    }
}
