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

        // System.err.println("oooooooooooooooooo" + tourist.getVisits());

        List<Visit> visits = tourist.getVisits();
        
        // Ensure the list is not empty before accessing it
        if (visits == null || visits.isEmpty()) {
            // If the list is empty, create a new Visit instance
            Visit visit = new Visit();
            visit.setDestination(destination);
            visit.setVisitedAt(dateFormat.format(date));
            
            // Add the new visit to the list
            visits = new ArrayList<>();
            visits.add(visit);
        } else {
            // If the list is not empty, use the existing visit
            Visit visit = visits.get(0);
            visit.setDestination(destination);
            visit.setVisitedAt(dateFormat.format(date));
        }
    
        tourist.setVisits(visits);
        touristRepository.save(tourist);
    }
    
    @Override
    public Tourist editTourist(Tourist tourist) {
        return this.touristRepository.save(tourist);
    }

    @Override
    public ResponseEntity<?> getTouristGraphData(String duration) {
        List<Tourist> allTourists = touristRepository.findAll();
        String startDate = getStartDate();
        Collection<String> dates = new ArrayList<>();

        // Convert the start date to LocalDate format
        String inputDateStr = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate inputDate = LocalDate.parse(inputDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

        // Get the current date
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate nextDay;
        String nextDayString = "";
        int counter = 0;
        dates.add(startDate);

        // Generate dates from the start date to today
        do {
            counter++;
            nextDay = inputDate.plusDays(counter);
            nextDayString = nextDay.format(DateTimeFormatter.ISO_LOCAL_DATE);
            dates.add(nextDayString + "-00-00-00");
        } while (!nextDayString.equals(today));

        Collection<Object> fullData = new ArrayList<>();
        LocalDate filterStartDate = getFilterStartDate(duration);

        dates.forEach((date -> {
            Collection<Object> timestampVsValue = new ArrayList<>();
            timestampVsValue.add(getTimestamp(date));
            addValue(timestampVsValue, dates);

            int touristCounter = 0;
            for (Tourist tourist : allTourists) {
                LocalDate touristVisitedDate = LocalDate.parse(tourist.getFirstVisitedDate().substring(0, 10));

                // Filter by duration
                if (touristVisitedDate.isAfter(filterStartDate) || touristVisitedDate.isEqual(filterStartDate)) {
                    if (tourist.getFirstVisitedDate().substring(0, 10).equals(date.substring(0, 10))) {
                        touristCounter++;
                    }
                }
            }

            timestampVsValue.add(touristCounter);
            fullData.add(timestampVsValue);
        }));

        return new ResponseEntity<>(fullData, HttpStatus.OK);
    }

    // Helper method to determine the start date for the filter based on the duration
    private LocalDate getFilterStartDate(String duration) {
        LocalDate today = LocalDate.now();
        switch (duration.toLowerCase()) {
            case "1month":
                return today.minusMonths(1);
            case "3months":
                return today.minusMonths(3);
            case "6months":
                return today.minusMonths(6);
            case "9months":
                return today.minusMonths(9);
            case "12months":
            case "1year":
                return today.minusYears(1);
            case "all":
            default:
                return LocalDate.MIN; // Return the minimum date to include all records
        }
    }

// Other existing methods remain unchanged...

    @Override
    public List<Tourist> fetchTourists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        List<Tourist> allTourists = touristRepository.findAll();
        List<Tourist> touristsToReturn = new ArrayList<>();
        Collection<Role> roles = user.getRoles();

        for (Role role : roles) {
            String userRole = role.getRoleName();
            if (userRole.equals("System Admin")) {
                touristsToReturn = touristRepository.findAll();
            } else if (userRole.equals("admin")) {
                for (Tourist tourist : allTourists) {
                    for (Visit visit : tourist.getVisits()) {
                        Destination destination = visit.getDestination();
                        if (destination == null) {
//                            System.err.println("Warning: Found a visit with a null destination for Tourist ID: " + tourist.getTouristId());
                            continue; // Skip this visit
                        }
                        if (destination.getDestinationId().equals(user.getDestination().getDestinationId())) {
                            touristsToReturn.add(tourist);
                            break; // No need to check other visits for the same tourist
                        }
                    }
                }
            }
        }

//        System.out.println(touristsToReturn);
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
//                System.out.println(startDate);
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