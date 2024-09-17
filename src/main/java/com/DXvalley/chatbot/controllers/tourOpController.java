package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.TourOperator;
import com.DXvalley.chatbot.repository.TourOPRRepository;
import com.DXvalley.chatbot.service.TourOpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/tourOperator")
public class tourOpController {
    @Autowired
    private TourOpService tourOpService;
@Autowired
private TourOPRRepository tourOPRRepository;
    @PostMapping("/registerTourOperator")
    public ResponseEntity<?> createTourOp(@RequestBody TourOperator tempUser) {
        return tourOpService.registerTourOrg(tempUser);
    }

    @GetMapping("/getTourOperators")
    public ResponseEntity<?> fetchTourOperators() {
        List<TourOperator> tourOperators = tourOpService.fetchTourOperators();
        return new ResponseEntity<>(tourOperators, HttpStatus.OK);
    }

    @PutMapping("/edit/{tourOperatorId}")
    TourOperator editTourOp(@RequestBody TourOperator tourOperator, @PathVariable Long tourOperatorId) {

        TourOperator tourOperator1 = this.tourOPRRepository.findByTourOperatorId(tourOperatorId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tourOperator1.setUpdatedAt(LocalDateTime.now().format(dateTimeFormatter));

        tourOperator1.setTourOrgName(tourOperator.getTourOrgName());
        tourOperator1.setOwnerFullName(tourOperator.getOwnerFullName());
        tourOperator1.setOwnerAddress(tourOperator.getOwnerAddress());
        tourOperator1.setEmail(tourOperator.getEmail());
        tourOperator1.setDestinations(tourOperator.getDestinations());
        tourOperator1.setPhoneNum(tourOperator.getPhoneNum());
        tourOperator1.setTourCategory(tourOperator.getTourCategory());
        tourOperator1.setTouristType(tourOperator.getTouristType());
        tourOperator1.setTinNum(tourOperator.getTinNum());
        tourOperator1.setFoundAt((tourOperator.getFoundAt()));

        System.err.println("Here Here Here"+tourOperator1);

        return tourOpService.editTourOp(tourOperator1);
    }

    @GetMapping("/getTourOperator/{tourOperatorId}")
    public ResponseEntity<?> getTourOpById(@PathVariable Long tourOperatorId) {
        var tourOperator = tourOPRRepository.findByTourOperatorId(tourOperatorId);
        if (tourOperator == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this Tour Operator!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tourOperator, HttpStatus.OK);
    }


//    @PutMapping("/edit/{tourOperatorId}")
//    public ResponseEntity<?> editTourOp(@PathVariable Long tourOperatorId, @RequestBody TourOperator tourOperator) {
//        TourOperator updatedTourOp = tourOpService.editTourOp(tourOperator);
//        return ResponseEntity.ok(updatedTourOp);
//    }

    @DeleteMapping("/delete/tourOperator/{tourOperatorId}")
//    public ResponseEntity<?> deleteTourOp(@PathVariable Long tourOperatorId) {
//        this.tourOPRRepository.deleteById(tourOperatorId);
//        return ResponseEntity.ok().build();
//    }
    void deletePackage(@PathVariable Long packageId) {
        this.tourOPRRepository.deleteById(packageId);
    }

}
