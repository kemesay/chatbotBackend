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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getEmployees")
    private ResponseEntity<?> fetchEmployees(){
        List<Employee> employees=employeeService.fetchEmployee();
        return new ResponseEntity<>(employees,HttpStatus.OK);
//        return new ResponseEntity<>(new createUserResponse("success","fetched"),HttpStatus.FOUND);
    }
    @GetMapping("/getEmployee/{employeeId}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Long employeeId) {
        var employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this employee!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/edit/{employeeId}")
    Employee editEmployee(@RequestBody Employee employee, @PathVariable Long employeeId) {
        Employee employee1 = this.employeeRepository.findByEmployeeId(employeeId);
        employee1.setFirstName(employee.getFirstName());
        employee1.setLastName(employee.getLastName());
        employee1.setMiddleName(employee.getMiddleName());
        employee1.setEmail(employee.getEmail());
        employee1.setOffice(employee.getOffice());
        employee1.setIsActive(employee.getIsActive());
        employee1.setPhoneNum(employee.getPhoneNum());

        return employeeService.editEmployee(employee1);
    }

    @DeleteMapping("/delete/employee/{employeeId}")
    void deleteEmployee(@PathVariable Long employeeId) {
        this.employeeRepository.deleteById(employeeId);
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
