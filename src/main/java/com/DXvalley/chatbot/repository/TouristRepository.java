package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TouristRepository extends JpaRepository<Tourist, Long> {
    Tourist findByPhoneNum(String phoneNum);

    Tourist findByPhoneNumAndFullName(String phoneNum, String fullName);

    Tourist findByTouristId(Long touristId);

    Tourist findByEmailOrPassportId(String email, String passportId);

    @Query("SELECT e FROM Tourist e ORDER BY e.firstVisitedDate ASC")
    List<Tourist> findFirstRegisteredEntity();

    @Query("SELECT COUNT(u) FROM Tourist u JOIN u.visits v WHERE v.destination = :destination")
    int countTouristsAtDestination(@Param("destination") Destination destination);



    @Query("SELECT COUNT(u) FROM Tourist u WHERE YEAR(CURRENT_DATE) - YEAR(TO_DATE(u.birthDate, 'yyyyMMdd')) BETWEEN ?1 AND ?2")
    Integer findByAgeRangeCount(int startAge, int endAge);

    @Query("SELECT COUNT(CASE WHEN u.gender = 'Female' THEN 1 END) AS female_count, " +
            "COUNT(CASE WHEN u.gender = 'Male' THEN 1 END) AS male_count " +
            "FROM Tourist u")
    Map<String, Long> findFemaleAndMaleCount();

}
