package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.Office;
import com.DXvalley.chatbot.repository.OfficeRepository;
import com.DXvalley.chatbot.service.OfficeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/office")
public class OfficeController {
    @Autowired
    private OfficeService officeService;
    @Autowired
    private OfficeRepository officeRepository;
    @PostMapping("/registerOffice")
    public ResponseEntity<?> createOffice(@RequestBody Office office) {
        Office office1=officeRepository.findByOfficeName(office.getOfficeName());
        OfficeController.ResponseMessage responseMessage;
        if (office1==null) {
            officeService.registerOffice(office);
            responseMessage = new OfficeController.ResponseMessage("success", "Office created successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else {
            responseMessage = new OfficeController.ResponseMessage("fail", "Office already exist");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getOffices")
    private ResponseEntity<?> fetchOffices(){
        List<Office> office=officeService.fetchOffices();
        return new ResponseEntity<>(office,HttpStatus.OK);
//        return new ResponseEntity<>(new createUserResponse("success","fetched"),HttpStatus.FOUND);
    }
    @GetMapping("/getOffice/{officeId}")
    public ResponseEntity<?> getByOfficeId(@PathVariable Long officeId) {
        var office = officeRepository.findByOfficeId(officeId);
        if (office == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this office!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(office, HttpStatus.OK);
    }
    @PutMapping("/edit/{officeId}")
    Office e(@RequestBody Office office, @PathVariable Long officeId) {
        Office office1 = this.officeRepository.findByOfficeId(officeId);
        office1.setOfficeName(office.getOfficeName());
        office1.setOfficeAddress(office.getOfficeAddress());
        office1.setOfficeLatitude(office.getOfficeLatitude());
        office1.setOfficeLongitude(office.getOfficeLongitude());
        office1.setDescription(office.getDescription());
        return officeService.editOffice(office1);
    }
    @DeleteMapping("/delete/office/{officeId}")
    void deleteOffice(@PathVariable Long officeId) {
        this.officeRepository.deleteById(officeId);
    }
    @Getter
    @Setter
    @AllArgsConstructor
    class ResponseMessage {
        String status;
        String description;
    }


}

