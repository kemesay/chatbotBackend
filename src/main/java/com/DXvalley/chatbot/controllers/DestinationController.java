package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.repository.DestinationRepository;
import com.DXvalley.chatbot.service.DestinationService;
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
@RestController
@RequestMapping("/destination")
public class DestinationController {
    @Autowired
    private DestinationService destinationService;
    @Autowired
    private DestinationRepository destinationRepository;
    @PostMapping("/registerDestination")
    public ResponseEntity<?> createDestination(@RequestBody Destination destination) {
        Destination destination1=destinationRepository.findByDestinationName(destination.getDestinationName());
        DestinationController.ResponseMessage responseMessage;
        if (destination1==null) {
            destinationService.registerDestination(destination);
            responseMessage = new DestinationController.ResponseMessage("success", "Destination registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else {
            responseMessage = new DestinationController.ResponseMessage("fail", "Destination  already exist");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    class ResponseMessage {
        String status;
        String description;
    }


}

