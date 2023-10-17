package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    Hotel findByName(String Name);
    Hotel findByHotelId(Long hotelId);

}
