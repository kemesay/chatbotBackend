package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.TourOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourOPRRepository extends JpaRepository<TourOperator,Long> {
    TourOperator findByTourOrgName(String tourOrgName);
    TourOperator findByTourOperatorId (Long tourOperatorId);

    TourOperator findByTinNum(String tinNum);
    @Query("SELECT COUNT(t) FROM TourOperator t JOIN t.destinations d WHERE d = :destination")
    int countTourOperatorsAtDestination(@Param("destination") Destination destination);

    @Query("SELECT o from TourOperator o JOIN o.destinations d WHERE d.name = :destinationName")
    List<TourOperator> findTourOperatorsAtDestination(String destinationName);
}
