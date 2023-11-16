package com.DXvalley.chatbot.service;
import com.DXvalley.chatbot.models.Destination;

import java.util.List;

public interface DestinationService {
    void registerDestination(Destination destination);

    List<Destination> fetchDestinations();
    Destination editDestination (Destination destination);

}
