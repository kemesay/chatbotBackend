package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristRepository extends JpaRepository<Tourist,Long>{
    Tourist findByPhoneNum(String phoneNum);
    Tourist findByTouristId (Long touristId);
    Tourist findByEmailOrPassportId(String email, String passportId);
}
