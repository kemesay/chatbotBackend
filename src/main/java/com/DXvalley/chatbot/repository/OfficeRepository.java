package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OfficeRepository extends JpaRepository<Office,Long> {
    Office findByOfficeName(String officeName);

}
