package com.DXvalley.chatbot.controllers;

import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.DestinationRepository;
import com.DXvalley.chatbot.service.DestinationService;
import com.DXvalley.chatbot.serviceImp.PackageServiceImp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destination")
public class DestinationController {
    @Autowired
    private DestinationService destinationService;
    @Autowired
    private DestinationRepository destinationRepository;

    @PostMapping("/registerDestination")
    public ResponseEntity<?> createDestination(@RequestBody Destination destination) {
        Destination destination1 = destinationRepository.findByName(destination.getName());
        DestinationController.ResponseMessage responseMessage;
        if (destination1 == null) {
            destinationService.registerDestination(destination);
            responseMessage = new DestinationController.ResponseMessage("success", "Destination registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage = new DestinationController.ResponseMessage("fail", "Destination  already exist");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getDestinations")
    private ResponseEntity<?> fetchDestinations() {

        List<Destination> destinations = destinationService.fetchDestinations();
        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }

    @GetMapping("/getDestination/{destinationId}")
    public ResponseEntity<?> getByDestinationId(@PathVariable Long destinationId) {
        var destination = destinationRepository.findByDestinationId(destinationId);
        if (destination == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this destination!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(destination, HttpStatus.OK);
    }

    @PutMapping("/edit/{destinationId}")
    Destination editDestination(@RequestBody Destination destination, @PathVariable Long destinationId) {
        Destination destination1 = this.destinationRepository.findByDestinationId(destinationId);
        destination1.setName(destination.getName());
        destination1.setAddress(destination.getAddress());
        destination1.setDescription(destination.getDescription());
        destination1.setLatitude(destination.getLatitude());
        destination1.setLongitude(destination.getLongitude());
        return destinationService.editDestination(destination1);
    }

    @DeleteMapping("/delete/destination/{destinationId}")
    void deleteDestination(@PathVariable Long destinationId) {
        this.destinationRepository.deleteById(destinationId);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class ResponseMessage {
        String status;
        String description;
    }


}

