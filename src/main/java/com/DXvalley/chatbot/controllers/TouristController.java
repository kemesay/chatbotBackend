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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/tourist")
public class TouristController{

    @Autowired
    private TouristService touristService;
    @Autowired
    private TouristRepository touristRepository;
    @PostMapping("/registerTourist")
    public ResponseEntity<?> createTourist(@RequestBody Tourist tourist) {

        var tourist1=touristRepository.findByPhoneNum(tourist.getPhoneNum());
        TouristController.ResponseMessage responseMessage;
        if (tourist1==null) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tourist.setVisitedAt(dateFormat.format(date));
            touristService.registerTourist(tourist);
            responseMessage = new TouristController.ResponseMessage("success", "Tourist Registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else {
            responseMessage = new TouristController.ResponseMessage("fail", "Tourist register fail");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
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

