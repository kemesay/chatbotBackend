package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Tourist;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TouristService {

    void registerTourist(Tourist tourist);
    List<Tourist> fetchTourists();
    Tourist editTourist (Tourist tourist);

    ResponseEntity<?> getTouristGraphData();
}
