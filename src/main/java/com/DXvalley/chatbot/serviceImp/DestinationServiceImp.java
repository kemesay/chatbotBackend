package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.repository.DestinationRepository;
import com.DXvalley.chatbot.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationServiceImp implements DestinationService {
    @Autowired
    DestinationRepository destinationRepository;
    @Override
    public void registerDestination(Destination destination) {destinationRepository.save(destination);}
    @Override
    public Destination editDestination(Destination destination) {return this.destinationRepository.save(destination);}
    @Override
    public List<Destination> fetchDestinations() { return destinationRepository.findAll();}

    }


