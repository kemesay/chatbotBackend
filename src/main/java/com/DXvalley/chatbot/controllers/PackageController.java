package com.DXvalley.chatbot.controllers;

import com.DXvalley.chatbot.models.TourPackage;
import com.DXvalley.chatbot.repository.TourPackageRepository;
import com.DXvalley.chatbot.service.TourPackageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.PackagePrivate;
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
@RequestMapping("/tourPackage")
public class PackageController {

    @Autowired
    private TourPackageService tourPackageService;
    @Autowired
    private TourPackageRepository tourPackageRepository;
    @PostMapping("/registerPackage")
    public ResponseEntity<?> createPackage(@RequestBody TourPackage tourPackage) {

        var package1=tourPackageRepository.findByPackageName(tourPackage.getPackageName());
        tourOpController.ResponseMessage responseMessage;
        if (package1==null) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            package1.setCreatedAt(dateFormat.format(date));
            tourPackageService.registerPackage(tourPackage);
            responseMessage = new tourOpController.ResponseMessage("success", "Package Registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else {
            responseMessage = new tourOpController.ResponseMessage("fail", "Package register fail");
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
