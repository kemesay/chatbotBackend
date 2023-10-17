package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DestinationRepository extends JpaRepository<Destination,Long> {
    Destination findByName(String Name);
    Destination findByDestinationId(Long destinationId);

}
