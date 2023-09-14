package com.driver.repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelRepository {
    private Map<String, Hotel> hotelDb = new HashMap<>();
    private Map<Integer, User> userDb = new HashMap<>();
    private List<Booking> bookingDb = new ArrayList<>();

    // Hotel-related methods
    public void addHotel(String hotelName, Hotel hotel) {
        hotelDb.put(hotelName, hotel);
    }

    public Hotel getHotel(String hotelName) {
        return hotelDb.get(hotelName);
    }

    public List<Hotel> getAllHotels() {
        return new ArrayList<>(hotelDb.values());
    }

//    public void updateFacilities(Hotel hotel, List<Facility> existingFacilities) {
//        // Assuming your Hotel class has a method to update facilities
//        hotel.updateFacilities(existingFacilities);
//    }

    // User-related methods
    public void addUser(User user) {
        userDb.put(user.getaadharCardNo(), user);
    }

    public User getUser(Integer aadharCard) {
        return userDb.get(aadharCard);
    }

    // Booking-related methods
    public void addBooking(Booking booking) {
        bookingDb.add(booking);
    }

    public List<Booking> getBookingsByAadharCard(Integer aadharCard) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookingDb) {
            if (Objects.equals(booking.getBookingAadharCard(), aadharCard)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }
}
