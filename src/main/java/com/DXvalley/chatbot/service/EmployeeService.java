package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    void registerEmployee(Employee employee);

    List<Employee> fetchEmployee();
    Employee editEmployee (Employee employee);

    ResponseEntity<?> getEmployeeGraphData();

}
