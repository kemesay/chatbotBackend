package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.exception.customException.ResourceNotFoundException;
import com.DXvalley.chatbot.models.*;
import com.DXvalley.chatbot.repository.TouristRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.TouristService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tourist")
public class TouristController {
    @Autowired
    private TouristService touristService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TouristRepository touristRepository;

    @PostMapping("/registerTourist")
    public ResponseEntity<?> createTourist(@RequestBody Tourist tourist) {
        var existingTourist = touristRepository.findByPhoneNumAndFullName(tourist.getPhoneNum(), tourist.getFullName());
        TouristController.ResponseMessage responseMessage;

        if (existingTourist == null) {
            touristService.registerTourist(tourist);
            responseMessage = new TouristController.ResponseMessage("success", "Tourist Registered successfully");

        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Users user = userRepository.findByEmailOrUsername(username, username);

            Destination destination = user.getDestination();
            List<Visit> updatedVisits = existingTourist.getVisits();

            Visit addedVisit = new Visit();
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
            addedVisit.setVisitedAt(dateFormat.format(date));
//            addedVisit.setDurationOfStay(tourist.getVisits().get(0).getDurationOfStay());
            addedVisit.setDestination(destination);
            updatedVisits.add(addedVisit);
            existingTourist.setVisits(updatedVisits);
            touristRepository.save(existingTourist);
            responseMessage = new TouristController.ResponseMessage("success", "Tourist successfully updated");
        }
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/getTourist")
    private ResponseEntity<?> fetchTourists() {
        List<Tourist> tourist = touristService.fetchTourists();
        return new ResponseEntity<>(tourist, HttpStatus.OK);
//        return new ResponseEntity<>(new createUserResponse("success","fetched"),HttpStatus.FOUND);
    }

    @GetMapping("/getTourists/{touristId}")
    public ResponseEntity<?> getByTouristId(@PathVariable Long touristId) {
        var tourist = touristRepository.findByTouristId(touristId);
        if (tourist == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this Tourist!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tourist, HttpStatus.OK);
    }

    @PutMapping("/edit/{touristId}")
    public Tourist editTourist(@RequestBody Tourist updatedTourist, @PathVariable Long touristId) {
        // Step 1: Retrieve the existing tourist and their visits from the database
        Tourist existingTourist = this.touristRepository.findByTouristId(touristId);
        if (existingTourist == null) {
            // Handle case where tourist is not found
            throw new ResourceNotFoundException("Tourist not found with id " + touristId);
        }

        // Step 2: Retrieve existing and updated visits
        List<Visit> existingVisits = existingTourist.getVisits();
        List<Visit> updatedVisits = updatedTourist.getVisits();

        // Step 3: Update only the durationOfStay in the existing visits
        for (Visit updatedVisit : updatedVisits) {
            for (Visit existingVisit : existingVisits) {
                if (existingVisit.getVisitId().equals(updatedVisit.getVisitId())) {
                    // Update only the durationOfStay field
                    existingVisit.setDurationOfStay(updatedVisit.getDurationOfStay());
                    break; // No need to check other visits once the match is found
                }
            }
        }

        // Step 4: Set the updated visits back to the existing tourist
        existingTourist.setVisits(existingVisits);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        existingTourist.setUpdatedAt(LocalDateTime.now().format(dateTimeFormatter));
        // Update other fields as necessary
        existingTourist.setTouristType(updatedTourist.getTouristType());
        existingTourist.setTourCategory(updatedTourist.getTourCategory());
        existingTourist.setCity(updatedTourist.getCity());
        existingTourist.setGender(updatedTourist.getGender());
        existingTourist.setEmail(updatedTourist.getEmail());
        existingTourist.setZipcode(updatedTourist.getZipcode());
        existingTourist.setCountry(updatedTourist.getCountry());
        existingTourist.setSubCity(updatedTourist.getSubCity());
        existingTourist.setFullName(updatedTourist.getFullName());
        existingTourist.setBirthDate(updatedTourist.getBirthDate());
        existingTourist.setPassportId(updatedTourist.getPassportId());

        // Step 5: Save the updated tourist back to the repository
        return touristRepository.save(existingTourist);
    }


    @DeleteMapping("/delete/tourist/{touristId}")
    void deleteTourist(@PathVariable Long touristId) {
        this.touristRepository.deleteById(touristId);
    }

    @GetMapping("/get-tourist-graph-data")
    public ResponseEntity<?> getTouristGraphData(@RequestParam(name = "duration", defaultValue = "all") String duration) {
        return touristService.getTouristGraphData(duration);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static
    class ResponseMessage {
        String status;
        String description;
    }

    @PostMapping("/getAgeRangeCount")
    ArrayList<Integer> getAgeRangeCount(@RequestBody ArrayList<AgeRange> ageRanges) {
        ArrayList<Integer> arrayOfAgeRangeCount = new ArrayList<>();
        Users user = getUser();
        ageRanges.forEach(ageRange -> {
            for (Role role :
                    user.getRoles()) {
                String userRole = role.getRoleName();
                if (userRole.equals("admin")) {
                    arrayOfAgeRangeCount.add(touristRepository.findByAgeRangeCountForDestination(ageRange.getStart(), ageRange.getEnd(), user.getDestination().getName()));
                } else if (userRole.equals("System Admin")) {
                    arrayOfAgeRangeCount.add(touristRepository.findByAgeRangeCount(ageRange.getStart(), ageRange.getEnd()));
                }
            }

        });

        return arrayOfAgeRangeCount;
    }

    @GetMapping("/findFemaleAndMaleCount")
    Map<String, Long> findFemaleAndMaleCount() {
        Users user = getUser();
        for (Role role :
                user.getRoles()) {
            String userRole = role.getRoleName();
            if (userRole.equals("admin")) {
                return touristRepository.findFemaleAndMaleCountForDestination(user.getDestination().getName());
            } else if (userRole.equals("System Admin")) {
                return touristRepository.findFemaleAndMaleCount();
            }
        }
        return null;
    }

    @GetMapping("/findInternationalAndDemoCount")
    Map<String, Long> findInternationalAndDemoCount() {
        Users user = getUser();
        for (Role role :
                user.getRoles()) {
            String userRole = role.getRoleName();
            if (userRole.equals("admin")) {
                return touristRepository.findInternationalAndDemosticCountForDestination(user.getDestination().getName());
            } else if (userRole.equals("System Admin")) {
                return touristRepository.findInternationalAndDemosticCount();
            }
        }
        return null;
    }

    private Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        return user;
    }
}

@Getter
@Setter
class AgeRange {
    int start;
    int end;
}