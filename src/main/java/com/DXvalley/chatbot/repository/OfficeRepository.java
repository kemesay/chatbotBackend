package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Feedback;
import com.DXvalley.chatbot.models.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office,Long> {
    Office findByName(String Name);
    Office findByOfficeId(Long officeId);
    @Query("SELECT o FROM Office o WHERE o.destination = :destination")
    List<Office> findOfficesAtDestination(Destination destination);


}
