package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Hotel;
import com.DXvalley.chatbot.models.Role;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.HotelRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.DXvalley.chatbot.DTO.ResponseMessage;

@Service
public class HotelServiceImp implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> registerHotel(Hotel hotel) {
        Hotel hotel1 = hotelRepository.findByName(hotel.getName());
        ResponseMessage responseMessage;
        if (hotel1 == null) {
            hotel.setDestination(getUser().getDestination());
            hotelRepository.save(hotel);
            responseMessage = new ResponseMessage("success", "Bank created successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage = new ResponseMessage("fail", "Hotel's branch already exist");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public Hotel editHotel(Hotel hotel) {
        return this.hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> fetchHotels() {


        Users user = getUser();
        List<Hotel> hotels = new ArrayList<>();
        for (Role userRole :
                user.getRoles()) {
            String role = userRole.getRoleName();
            if (role.equals("System Admin")) {
                hotels.addAll(hotelRepository.findAll());
            } else if (role.equals("admin")) {
                String destinationName = user.getDestination().getName();
                hotels.addAll(hotelRepository.findHotelsAtDestination(destinationName));
            } else {

            }
        }
        return hotels;
    }

    private Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        return user;
    }
}
