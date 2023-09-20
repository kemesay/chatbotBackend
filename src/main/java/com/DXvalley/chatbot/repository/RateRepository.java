package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.Office;
import com.DXvalley.chatbot.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RateRepository extends JpaRepository<Rate,Long> {

    Rate findByUserId(Long UserId);

}
