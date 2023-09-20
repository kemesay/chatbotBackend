package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.Tourist;
import com.DXvalley.chatbot.repository.TouristRepository;
import com.DXvalley.chatbot.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TouristServiceIpm implements TouristService {
    @Autowired
    TouristRepository touristRepository;
    @Override
    public void registerTourist(Tourist tourist) {
        touristRepository.save(tourist);
    }
}