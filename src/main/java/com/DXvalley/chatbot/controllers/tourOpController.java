package com.DXvalley.chatbot.controllers;

import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.TourOperator;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.TourOPRRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.TourOpService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tourOperator")
public class tourOpController {
    @Autowired
    private TourOpService tourOpService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TourOPRRepository tourOPRRepository;

    @PostMapping("/registerTourOperator")
    public ResponseEntity<?> createTourOp(@RequestBody Users user) {
       return tourOpService.registerTourOrg(user);
    }

    @GetMapping("/getTourOperators")
    private ResponseEntity<?> fetchTourOperators() {
        List<TourOperator> tourOperators = tourOpService.fetchTourOperators();
        return new ResponseEntity<>(tourOperators, HttpStatus.OK);
    }

    @GetMapping("/getTourOp/{tourOperatorId}")
    public ResponseEntity<?> getByTourOperatorId(@PathVariable Long tourOperatorId) {
        var tourOperator = tourOPRRepository.findByTourOperatorId(tourOperatorId);
        if (tourOperator == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this tour Operator!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tourOperator, HttpStatus.OK);
    }

    @PutMapping("/edit/{tourOperatorId}")
    TourOperator editTourOp(@RequestBody TourOperator tourOperator, @PathVariable Long tourOperatorId) {
        TourOperator tourOperator1 = this.tourOPRRepository.findByTourOperatorId(tourOperatorId);
        tourOperator1.setTourOrgName(tourOperator.getTourOrgName());
        tourOperator1.setOwnerFullName(tourOperator.getOwnerFullName());
        tourOperator1.setOwnerAddress(tourOperator.getOwnerAddress());
        tourOperator1.setTourCategory(tourOperator.getTourCategory());
        tourOperator1.setFoundAt(tourOperator.getFoundAt());
        tourOperator1.setTinNum(tourOperator.getTinNum());
        return tourOpService.editTourOp(tourOperator1);
    }

    @DeleteMapping("/delete/tourOperator/{tourOperatorId}")
    void deleteTourOp(@PathVariable Long tourOperatorId) {
        this.tourOPRRepository.deleteById(tourOperatorId);
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static
    class ResponseMessage {
        String status;
        String description;
    }

}

