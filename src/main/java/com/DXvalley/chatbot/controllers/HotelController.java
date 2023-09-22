package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Hotel;
import com.DXvalley.chatbot.repository.HotelRepository;
import com.DXvalley.chatbot.service.HotelService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/getHotels")
    private ResponseEntity<?> fetchHotels(){
        List<Hotel> hotel=hotelService.fetchHotels();
        return new ResponseEntity<>(hotel,HttpStatus.OK);
//        return new ResponseEntity<>(new createUserResponse("success","fetched"),HttpStatus.FOUND);
    }
    @GetMapping("/getHotel/{hotelId}")
    public ResponseEntity<?> getByHotelId(@PathVariable Long hotelId) {
        var hotel = hotelRepository.findByHotelId(hotelId);
        if (hotel == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this hotel!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @PutMapping("/edit/{hotelId}")
    Hotel editHotel(@RequestBody Hotel hotel, @PathVariable Long hotelId) {
        Hotel hotel1 = this.hotelRepository.findByHotelId(hotelId);
        hotel1.setHotelName(hotel.getHotelName());
        hotel1.setHotelAddress(hotel.getHotelAddress());
        hotel1.setHotelDescription(hotel.getHotelDescription());
        hotel1.setHotelLatitude(hotel.getHotelLatitude());
        hotel1.setHotelLongitude(hotel.getHotelLongitude());
        return hotelService.editHotel(hotel1);
    }

    @DeleteMapping("/delete/hotel/{hotelId}")
    void deleteHotel(@PathVariable Long hotelId) {
        this.hotelRepository.deleteById(hotelId);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class ResponseMessage {
        String status;
        String description;
    }


}

