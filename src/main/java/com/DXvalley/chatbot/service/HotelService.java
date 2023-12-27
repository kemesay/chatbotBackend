package com.DXvalley.chatbot.service;
import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Hotel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HotelService {
    ResponseEntity<?> registerHotel(Hotel hotel);

    List<Hotel> fetchHotels();
    Hotel editHotel (Hotel hotel);
}