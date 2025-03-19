package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.Hotel;
import com.DXvalley.chatbot.repository.HotelRepository;
import com.DXvalley.chatbot.service.HotelService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Hotel APIs.")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelRepository hotelRepository;
    @PostMapping("/registerHotel")
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotel) {
        return  hotelService.registerHotel(hotel);

    }
    @GetMapping("/getHotels")
    private ResponseEntity<?> fetchHotels(){
        List<Hotel> hotel=hotelService.fetchHotels();
        return new ResponseEntity<>(hotel,HttpStatus.OK);
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

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        hotel1.setUpdatedAt(LocalDateTime.now().format(dateTimeFormatter));
        hotel1.setName(hotel.getName());
        hotel1.setAddress(hotel.getAddress());
        hotel1.setDescription(hotel.getDescription());
        hotel1.setLatitude(hotel.getLatitude());
        hotel1.setLongitude(hotel.getLongitude());
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

