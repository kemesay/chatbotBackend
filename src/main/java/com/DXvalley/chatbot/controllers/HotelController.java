package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.Hotel;
import com.DXvalley.chatbot.repository.HotelRepository;
import com.DXvalley.chatbot.service.HotelService;
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
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelRepository hotelRepository;
    @PostMapping("/registerHotel")
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotel) {
        Hotel hotel1=hotelRepository.findByHotelName(hotel.getHotelName());
        HotelController.ResponseMessage responseMessage;
        if (hotel1==null) {
            hotelService.registerHotel(hotel);
            responseMessage = new HotelController.ResponseMessage("success", "Hotel registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else {
            responseMessage = new HotelController.ResponseMessage("fail", "hotel already exist");
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

