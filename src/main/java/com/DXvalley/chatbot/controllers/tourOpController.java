package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.TourOpertaor;
import com.DXvalley.chatbot.models.Tourist;
import com.DXvalley.chatbot.repository.TourOPRRepository;
import com.DXvalley.chatbot.service.TourOpService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tourOper")
public class tourOpController {
    @Autowired
    private TourOpService tourOpService;
    @Autowired
    private TourOPRRepository tourOPRRepository;
    @PostMapping("/registerTourOrg")
    public ResponseEntity<?> createTourOp(@RequestBody TourOpertaor tourOpertaor) {

        var tourOp1=tourOPRRepository.findByTinNum(tourOpertaor.getTinNum());
        tourOpController.ResponseMessage responseMessage;
        if (tourOp1==null) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tourOpertaor.setFoundAt(dateFormat.format(date));
            tourOpService.registerTourOrg(tourOpertaor);


            responseMessage = new tourOpController.ResponseMessage("success", "TourOperator Registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else {
            responseMessage = new tourOpController.ResponseMessage("fail", "TourOperator register fail");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getTourOPs")
    private ResponseEntity<?> fetchTourOperators(){
        List<TourOpertaor> tourOpertaors=tourOpService.fetchTourOperators();
        return new ResponseEntity<>(tourOpertaors,HttpStatus.OK);
//        return new ResponseEntity<>(new createUserResponse("success","fetched"),HttpStatus.FOUND);
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
    TourOpertaor editTourOp(@RequestBody TourOpertaor tourOpertaor, @PathVariable Long tourOperatorId) {
        TourOpertaor tourOpertaor1 = this.tourOPRRepository.findByTourOperatorId(tourOperatorId);
        tourOpertaor1.setTourOrgName(tourOpertaor.getTourOrgName());
        tourOpertaor1.setOwnerFullName(tourOpertaor.getOwnerFullName());
        tourOpertaor1.setOwnerAddress(tourOpertaor.getOwnerAddress());
        tourOpertaor1.setEmail(tourOpertaor.getEmail());
        tourOpertaor1.setPhoneNum(tourOpertaor.getPhoneNum());
        tourOpertaor1.setTourCategory(tourOpertaor.getTourCategory());
        tourOpertaor1.setFoundAt(tourOpertaor.getFoundAt());
        tourOpertaor1.setTinNum(tourOpertaor.getTinNum());
        tourOpertaor1.setDestinations(tourOpertaor.getDestinations());

        return tourOpService.editTourOp(tourOpertaor1);
    }
    @DeleteMapping("/delete/tourist/{touristId}")
    void deleteTourOp(@PathVariable Long tourOperatorId) {
        this.tourOPRRepository.deleteById(tourOperatorId);
    }



    @Getter
    @Setter
    @AllArgsConstructor
    static
    class ResponseMessage {
        String status;
        String description;
    }

}

