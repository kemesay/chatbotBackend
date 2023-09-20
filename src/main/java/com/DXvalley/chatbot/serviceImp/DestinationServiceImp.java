package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.repository.DestinationRepository;
import com.DXvalley.chatbot.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DestinationServiceImp implements DestinationService {
    @Autowired
    DestinationRepository destinationRepository;
    @Override
    public void registerDestination(Destination destination) {destinationRepository.save(destination);

    }

}
