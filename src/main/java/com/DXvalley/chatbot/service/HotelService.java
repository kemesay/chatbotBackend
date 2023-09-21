package com.DXvalley.chatbot.service;
import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Hotel;

import java.util.List;

public interface HotelService {
    void registerHotel(Hotel hotel);

    List<Hotel> fetchHotels();
    Hotel editHotel (Hotel hotel);
}