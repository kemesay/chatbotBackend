package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.TourOpertaor;
import com.DXvalley.chatbot.repository.TourOPRRepository;
import com.DXvalley.chatbot.service.TourOpService;
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
            tourOp1.setFoundAt(dateFormat.format(date));
            tourOpService.registerTourOrg(tourOpertaor);
            responseMessage = new tourOpController.ResponseMessage("success", "TourOperator Registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else {
            responseMessage = new tourOpController.ResponseMessage("fail", "TourOperator register fail");
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

