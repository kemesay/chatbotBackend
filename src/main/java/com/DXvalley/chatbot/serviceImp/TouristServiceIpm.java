package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.Tourist;
import com.DXvalley.chatbot.repository.TouristRepository;
import com.DXvalley.chatbot.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TouristServiceIpm implements TouristService {
    @Autowired
    TouristRepository touristRepository;

    @Override
    public void registerTourist(Tourist tourist) {
        touristRepository.save(tourist);
    }

    @Override
    public Tourist editTourist(Tourist tourist) {
        return this.touristRepository.save(tourist);
    }

    @Override
    public ResponseEntity<?> getTouristGraphData() {
        Tourist firstRegisteredTourist = touristRepository.findFirstRegisteredEntity();
        String startDate = firstRegisteredTourist.getVisitedAt();
        Collection<String> dates = new ArrayList<>();

        String inputDateStr = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate inputDate = LocalDate.parse(inputDateStr, DateTimeFormatter.ISO_LOCAL_DATE);


        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate nextDay;
        String nextDayString = "";
        Integer counter = 0;
        dates.add(startDate);

        do {
            counter++;
            nextDay = inputDate.plusDays(counter);
            nextDayString = nextDay.format(DateTimeFormatter.ISO_LOCAL_DATE);
            dates.add(nextDayString + "-00-00-00");
        }
        while (!nextDayString.equals(today));

        Collection<Tourist> tourists = new ArrayList<>();
        tourists.addAll(touristRepository.findAll());

        Collection<Object> fullData = new ArrayList<>();
        dates.forEach((date -> {
            Collection<Object> timestampVsValue = new ArrayList<>();
            timestampVsValue.add(getTimestamp(date));
            timestampVsValue.add(0.95);
            fullData.add(timestampVsValue);
        }));


        return new ResponseEntity<>(fullData, HttpStatus.OK);
    }

    @Override
    public List<Tourist> fetchTourists() {
        return touristRepository.findAll();
    }

    Long getTimestamp(String visitedAt) {
        System.out.println(visitedAt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime localDateTime = LocalDateTime.parse(visitedAt, formatter);


        // Extract individual components
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        LocalDateTime dateTime = LocalDateTime.of(year, Month.of(month), day, hour, minute, second);
        Instant instant = dateTime.toInstant(ZoneOffset.UTC);

        return instant.toEpochMilli();
    }
}