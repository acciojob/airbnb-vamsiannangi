package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelManagementService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelManagementService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public String addHotel(Hotel hotel) {
        if (hotel == null || hotel.getHotelName() == null) {
            return "FAILURE";
        }

        if (hotelRepository.getHotel(hotel.getHotelName()) != null) {
            return "FAILURE";
        }

        hotelRepository.addHotel(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        hotelRepository.addUser(user); // Assuming your repository method doesn't return anything
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        Map<String, Integer> facilitiesCount = new HashMap<>();
        List<Hotel> hotels = hotelRepository.getAllHotels();

        for (Hotel hotel : hotels) {
            List<Facility> facilities = hotel.getFacilities();
            for (Facility facility : facilities) {
                String facilityName = facility.toString();
                facilitiesCount.put(facilityName, facilitiesCount.getOrDefault(facilityName, 0) + 1);
            }
        }

        if (facilitiesCount.isEmpty()) {
            return ""; // No hotels with facilities
        }

        int maxCount = Collections.max(facilitiesCount.values());
        List<String> maxFacilities = facilitiesCount.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Collections.sort(maxFacilities);
        return maxFacilities.get(0);
    }

    public int bookARoom(Booking booking) {
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);

        Hotel hotel = hotelRepository.getHotel(booking.getHotelName());
        if (hotel == null || booking.getNoOfRooms() > hotel.getAvailableRooms()) {
            return -1;
        }

        int totalAmount = booking.getNoOfRooms() * hotel.getPricePerNight();
        booking.setAmountToBePaid(totalAmount);

        // Update available rooms in the hotel
        hotel.setAvailableRooms(hotel.getAvailableRooms() - booking.getNoOfRooms());

        hotelRepository.addBooking(booking);
        return totalAmount;
    }

    public List<Booking> getBookings(Integer aadharCard) {
        return hotelRepository.getBookingsByAadharCard(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelRepository.getHotel(hotelName);
        if (hotel == null) {
            return null;
        }

        List<Facility> existingFacilities = hotel.getFacilities();

        for (Facility newFacility : newFacilities) {
            if (!existingFacilities.contains(newFacility)) {
                existingFacilities.add(newFacility);
            }
        }
        // Set the updated facilities directly in the hotel
        hotel.setFacilities(existingFacilities);

        return hotel;
    }
}
