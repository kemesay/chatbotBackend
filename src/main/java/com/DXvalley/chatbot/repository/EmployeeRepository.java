package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.Employee;
import com.DXvalley.chatbot.models.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByPhoneNum(String phoneNum);
    Employee findByEmployeeId(Long employeeId);

    @Query("SELECT e FROM Employee e ORDER BY e.registeredAt ASC")
    List<Employee> findFirstRegisteredEmployeeEntity();

}
