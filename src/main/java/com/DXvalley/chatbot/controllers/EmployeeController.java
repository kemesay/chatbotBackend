package com.DXvalley.chatbot.controllers;

import com.DXvalley.chatbot.models.Employee;
import com.DXvalley.chatbot.repository.EmployeeRepository;
import com.DXvalley.chatbot.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @PostMapping("/registerEmployee")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {

        var employee1=employeeRepository.findByPhoneNum(employee.getPhoneNum());
        ResponseMessage responseMessage;
        if (employee1==null) {
            employeeService.registerEmployee(employee);
            responseMessage = new ResponseMessage("success", "employee created successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else {
            responseMessage = new ResponseMessage("fail", "employee already exist");
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
