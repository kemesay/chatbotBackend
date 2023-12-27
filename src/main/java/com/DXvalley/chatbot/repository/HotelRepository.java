package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    Hotel findByName(String Name);
    Hotel findByHotelId(Long hotelId);
    @Query("SELECT h from Hotel h WHERE h.destination.name = :destinationName")
    List<Hotel> findHotelsAtDestination(String destinationName);


}
