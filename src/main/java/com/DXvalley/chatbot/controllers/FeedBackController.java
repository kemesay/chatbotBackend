package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.Feedback;
import com.DXvalley.chatbot.repository.FeedbackRepository;
import com.DXvalley.chatbot.service.FeedbackService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedBackController {
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackRepository feedbackRepository;
    @PostMapping("/giveFeedback")
    public ResponseEntity<?> createFeedback(@RequestBody Feedback feedback) {
        Feedback feedback1=feedbackRepository.findByFeedbackId(feedback.getFeedbackId());
        FeedBackController.ResponseMessage responseMessage;
        if (feedback1==null) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            feedback.setSentAt(dateFormat.format(date));

            feedbackService.giveFeedback(feedback);
            responseMessage = new FeedBackController.ResponseMessage("success", "your Feedback submitted successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        else if (feedback1!=null) {
            feedback1.setFeedbackText(feedback.getFeedbackText());
            feedbackService.giveFeedback(feedback1);
            responseMessage = new FeedBackController.ResponseMessage("success", "your feedback edited successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        else {
            responseMessage = new FeedBackController.ResponseMessage("fail", "Try again to submit your feedback");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/fetchall")
    private ResponseEntity<?> fetchFeedbacks(){
        List<Feedback> feedbacks=feedbackService.fetchFeedbacks();
        return new ResponseEntity<>(feedbacks,HttpStatus.OK);
//        return new ResponseEntity<>(new createUserResponse("success","fetched"),HttpStatus.FOUND);
    }



    @Getter
    @Setter
    @AllArgsConstructor
    class ResponseMessage {
        String status;
        String description;
    }



}
