package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination,Long> {
    Destination findByName(String Name);
    Destination findByDestinationId(Long destinationId);

}
