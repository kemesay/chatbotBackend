package com.DXvalley.chatbot.controllers;

import com.DXvalley.chatbot.models.Tourist;
import com.DXvalley.chatbot.repository.TouristRepository;
import com.DXvalley.chatbot.service.TouristService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tourist")
public class TouristController {
    @Autowired
    private TouristService touristService;
    @Autowired
    private TouristRepository touristRepository;

    @PostMapping("/registerTourist")
    public ResponseEntity<?> createTourist(@RequestBody Tourist tourist) {
        var tourist1 = touristRepository.findByPhoneNum(tourist.getPhoneNum());
        TouristController.ResponseMessage responseMessage;
        if (tourist1 == null) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
            tourist.setVisitedAt(dateFormat.format(date));
            touristService.registerTourist(tourist);
            responseMessage = new TouristController.ResponseMessage("success", "Tourist Registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage = new TouristController.ResponseMessage("fail", "Tourist register fail");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
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
    Tourist editTourist(@RequestBody Tourist tourist, @PathVariable Long touristId) {
        Tourist tourist1 = this.touristRepository.findByTouristId(touristId);
        tourist1.setTouristType(tourist.getTouristType());
        tourist1.setCity(tourist.getCity());
        tourist1.setGender(tourist.getGender());
        tourist1.setEmail(tourist.getEmail());
        tourist1.setZipcode(tourist.getZipcode());
        tourist1.setCountry(tourist.getTouristType());
        tourist1.setCity(tourist.getCity());
        tourist1.setFullName(tourist.getGender());
        tourist1.setDurationOfStay(tourist.getEmail());
        tourist1.setBirthDate(tourist.getBirthDate());
        tourist1.setDestination(tourist.getDestination());
        tourist1.setPassportId(tourist.getPassportId());

        return touristService.editTourist(tourist1);
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