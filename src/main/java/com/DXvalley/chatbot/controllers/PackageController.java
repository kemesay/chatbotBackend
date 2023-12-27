package com.DXvalley.chatbot.controllers;

import com.DXvalley.chatbot.models.TourPackage;
import com.DXvalley.chatbot.repository.TourPackageRepository;
import com.DXvalley.chatbot.service.TourPackageService;
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
@RequestMapping("/tourPackage")
public class PackageController {

    @Autowired
    private TourPackageService tourPackageService;
    @Autowired
    private TourPackageRepository tourPackageRepository;

    @PostMapping("/registerPackage")
    public ResponseEntity<?> createPackage(@RequestBody TourPackage tourPackage) {
        return tourPackageService.registerPackage(tourPackage);

    }

    @GetMapping("/getPackages")
    private ResponseEntity<?> fetchPackages() {
        List<TourPackage> tourPackages = tourPackageService.fetchPackages();
        return new ResponseEntity<>(tourPackages, HttpStatus.OK);
    }

    @GetMapping("/getpackages/{packageId}")
    public ResponseEntity<?> getByPackageId(@PathVariable Long packageId) {
        var tourPackage = tourPackageRepository.findByPackageId(packageId);
        if (tourPackage == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this package!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tourPackage, HttpStatus.OK);
    }

    @PutMapping("/edit/{packageId}")
    TourPackage editTourPackage(@RequestBody TourPackage tourPackage, @PathVariable Long packageId) {
        TourPackage tourPackage1 = this.tourPackageRepository.findByPackageId(packageId);
        tourPackage1.setPackageName(tourPackage.getPackageName());
        tourPackage1.setPackageDescription(tourPackage.getPackageDescription());
//        tourPackage1.setDestination(tourPackage.getDestination());
        tourPackage1.setDepartureDates(tourPackage.getDepartureDates());
        tourPackage1.setMaxGroup(tourPackage.getMaxGroup());
        tourPackage1.setPackagePricePerPerson(tourPackage.getPackagePricePerPerson());
        tourPackage1.setStayDuration(tourPackage.getStayDuration());
        tourPackage1.setTourOperator(tourPackage.getTourOperator());
        return tourPackageService.editTourPackage(tourPackage1);
    }

    @DeleteMapping("/delete/package/{packageId}")
    void deletePackage(@PathVariable Long packageId) {
        this.tourPackageRepository.deleteById(packageId);
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
