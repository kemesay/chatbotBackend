package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TouristRepository extends JpaRepository<Tourist,Long>{
    Tourist findByPhoneNum(String phoneNum);
    Tourist findByTouristId (Long touristId);
    Tourist findByEmailOrPassportId(String email, String passportId);
    @Query("SELECT e FROM Tourist e ORDER BY e.visitedAt ASC")
    Tourist findFirstRegisteredEntity();
}
