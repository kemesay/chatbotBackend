package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Employee;
import com.DXvalley.chatbot.models.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByPhoneNum(String phoneNum);

    Employee findByEmployeeId(Long employeeId);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.destination = :destination")
    int countDestinationEmployees(Destination destination);
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.destination.destinationId = :destinationId")
    int countEmployeesAtDestination(@Param("destinationId") Long destinationId);
    @Query("SELECT e from Employee e WHERE e.destination.name = :destinationName")
    List<Employee> findEmployeesAtDestination(String destinationName);
    @Query("SELECT e FROM Employee e ORDER BY e.registeredAt ASC")
    List<Employee> findFirstRegisteredEmployeeEntity();

}
