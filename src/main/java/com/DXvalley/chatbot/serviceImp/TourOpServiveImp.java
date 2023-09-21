package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.TourOpertaor;
import com.DXvalley.chatbot.repository.TourOPRRepository;
import com.DXvalley.chatbot.service.TourOpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TourOpServiveImp implements TourOpService {
    @Autowired
    TourOPRRepository tourOPRRepository;
    @Override
    public void registerTourOrg(TourOpertaor tourOpertaor) {
        tourOPRRepository.save(tourOpertaor);
    }
    @Override
    public TourOpertaor editTourOp(TourOpertaor tourOpertaor) {
        return this.tourOPRRepository.save(tourOpertaor);
    }
    @Override
    public List<TourOpertaor> fetchTourOperators() { return tourOPRRepository.findAll();}
}