package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.TourOpertaor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourOPRRepository extends JpaRepository<TourOpertaor,Long> {
    TourOpertaor findByTourOrgName(String tourOrgName);
    TourOpertaor findByTourOperatorId (Long tourOperatorId);
    TourOpertaor findByTinNum(String tinNum);
}
