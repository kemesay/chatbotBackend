package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Employee;

import java.util.List;

public interface EmployeeService {
    void registerEmployee(Employee employee);

    List<Employee> fetchEmployee();
    Employee editEmployee (Employee employee);
}
