package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByPhoneNum(String phoneNum);
    Employee findByEmployeeId(Long employeeId);

}
