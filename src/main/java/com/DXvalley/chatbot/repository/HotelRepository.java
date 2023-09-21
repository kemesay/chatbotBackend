package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    Hotel findByHotelName(String hotelName);
    Hotel findByHotelId(Long hotelId);

}
