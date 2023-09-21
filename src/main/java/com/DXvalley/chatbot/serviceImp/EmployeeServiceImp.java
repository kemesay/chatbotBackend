package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.Employee;
import com.DXvalley.chatbot.repository.EmployeeRepository;
import com.DXvalley.chatbot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public void registerEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
    @Override
    public Employee editEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }
    @Override
    public List<Employee> fetchEmployee() { return employeeRepository.findAll();}
}
