package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.TourOpertaor;
import com.DXvalley.chatbot.repository.TourOPRRepository;
import com.DXvalley.chatbot.service.TourOpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourOpServiveImp implements TourOpService {
    @Autowired
    TourOPRRepository tourOPRRepository;
    @Override
    public void registerTourOrg(TourOpertaor tourOpertaor) {
        tourOPRRepository.save(tourOpertaor);
    }
}