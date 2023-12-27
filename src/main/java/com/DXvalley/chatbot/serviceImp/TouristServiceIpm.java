package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.*;
import com.DXvalley.chatbot.repository.TouristRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

import java.util.*;

@Service
public class TouristServiceIpm implements TouristService {
    @Autowired
    TouristRepository touristRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerTourist(Tourist tourist) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        tourist.setFirstVisitedDate(dateFormat.format(date));
        Users user = userRepository.findByEmailOrUsername(username, username);
        Destination destination = user.getDestination();
        Visit visit = tourist.getVisits().get(0);

        visit.setDestination(destination);
        visit.setVisitedAt(dateFormat.format(date));
        ArrayList<Visit> visits = new ArrayList<>();
        visits.add(visit);
        tourist.setVisits(visits);
        touristRepository.save(tourist);
    }

    @Override
    public Tourist editTourist(Tourist tourist) {
        return this.touristRepository.save(tourist);
    }

    @Override
    public ResponseEntity<?> getTouristGraphData() {
        List<Tourist> allTourists = touristRepository.findAll();
        String startDate = getStartDate();
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

        Collection<Object> fullData = new ArrayList<>();
        dates.forEach((date -> {
            Collection<Object> timestampVsValue = new ArrayList<>();
            timestampVsValue.add(getTimestamp(date));
            addValue(timestampVsValue, dates);

            int touristCounter = 0;

            for (Tourist tourist : allTourists) {
                if (tourist.getFirstVisitedDate().substring(0, 10).equals(date.substring(0, 10))) {
                    touristCounter++;
                }
            }

            timestampVsValue.add(touristCounter);
            fullData.add(timestampVsValue);
        }));


        return new ResponseEntity<>(fullData, HttpStatus.OK);
    }

    @Override
    public List<Tourist> fetchTourists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        List<Tourist> allTourists = touristRepository.findAll();
        List<Tourist> touristsToReturn = new ArrayList<>();
        Collection<Role> roles = user.getRoles();
        for (Role role :
                roles) {
            String userRole = role.getRoleName();
            if (userRole.equals("System Admin")) {
                touristsToReturn = touristRepository.findAll();
            } else if (userRole.equals("admin")) {

                for (Tourist tourist :
                        allTourists) {
                    for (Visit visit :
                            tourist.getVisits()) {
                        boolean isTouristAtTheDestination = visit.getDestination().getDestinationId().equals(user.getDestination().getDestinationId());
                        if (isTouristAtTheDestination) {
                            touristsToReturn.add(tourist);
                        }
                    }
                }
            }
        }
        return touristsToReturn;
    }

    void addValue(Collection<Object> timestampVsValue, Collection<String> dates) {

    }

    Long getTimestamp(String visitedAt) {
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

    private String getStartDate() {
        Tourist firstTourist = null;
        String startDate = "";
        Users user = getUser();
        for (Role role :
                user.getRoles()) {
            String userRole = role.getRoleName();
            if (userRole.equals("admin")) {
                firstTourist = touristRepository.findFirstRegisteredTouristAtDestination(user.getDestination().getName());
                startDate = firstTourist.getFirstVisitedDate();
                return startDate;
            } else if (userRole.equals("System Admin")) {
                firstTourist = touristRepository.findFirstRegisteredEntity();
                startDate = firstTourist.getFirstVisitedDate();
                return startDate;
            }
        }
        return "";
    }

    public Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        return user;
    }
}