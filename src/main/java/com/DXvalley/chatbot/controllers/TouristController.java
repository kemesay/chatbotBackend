package com.DXvalley.chatbot.controllers;

import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Tourist;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.models.Visit;
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
import java.util.*;

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
            addedVisit.setDurationOfStay(tourist.getVisits().get(0).getDurationOfStay());
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
    Tourist editTourist(@RequestBody Tourist updatedTourist, @PathVariable Long touristId) {
        Tourist existingTourist = this.touristRepository.findByTouristId(touristId);
        List<Visit> existingVisits = existingTourist.getVisits();
        List<Visit> updatedVisits = updatedTourist.getVisits();

        for (Visit updatedVisit : updatedVisits) {
            for (Visit existingVisit : existingVisits) {
                if (existingVisit.getVisitId().equals(updatedVisit.getVisitId())) {
                    existingVisit.setDurationOfStay(updatedVisit.getDurationOfStay());
                }
            }
        }

        // Set the updated visits to the existing tourist
        existingTourist.setVisits(existingVisits);

        existingTourist.setTouristType(updatedTourist.getTouristType());
        existingTourist.setCity(updatedTourist.getCity());
        existingTourist.setGender(updatedTourist.getGender());
        existingTourist.setEmail(updatedTourist.getEmail());
        existingTourist.setZipcode(updatedTourist.getZipcode());
        existingTourist.setCountry(updatedTourist.getTouristType());
        existingTourist.setCity(updatedTourist.getCity());
        existingTourist.setFullName(updatedTourist.getGender());
//        tourist1.setDurationOfStay(tourist.getEmail());
        existingTourist.setBirthDate(updatedTourist.getBirthDate());
//        tourist1.setDestination(tourist.getDestination());
        existingTourist.setPassportId(updatedTourist.getPassportId());
        touristRepository.save(existingTourist);
        return null;
    }

    @DeleteMapping("/delete/tourist/{touristId}")
    void deleteTourist(@PathVariable Long touristId) {
        this.touristRepository.deleteById(touristId);
    }

    @GetMapping("/get-tourist-graph-data")
    ResponseEntity<?> getTouristGraphData() {
        return touristService.getTouristGraphData();
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
        ageRanges.forEach(ageRange -> {
            arrayOfAgeRangeCount.add(touristRepository.findByAgeRangeCount(ageRange.getStart(), ageRange.getEnd()));
        });

        return arrayOfAgeRangeCount;
    }

    @GetMapping("/findFemaleAndMaleCount")
    Map<String, Long> findFemaleAndMaleCount() {
        return touristRepository.findFemaleAndMaleCount();
    }

}


@Getter
@Setter
class AgeRange {
    int start;
    int end;
}