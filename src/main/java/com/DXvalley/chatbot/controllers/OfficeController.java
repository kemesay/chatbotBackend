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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @Getter
    @Setter
    @AllArgsConstructor
    class ResponseMessage {
        String status;
        String description;
    }


}

