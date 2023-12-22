package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.Employee;
import com.DXvalley.chatbot.repository.EmployeeRepository;
import com.DXvalley.chatbot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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

    @Override
    public ResponseEntity<?> getEmployeeGraphData(){
        List<Employee> employeeList = employeeRepository.findFirstRegisteredEmployeeEntity();
        List<Employee> allEmployees = employeeRepository.findAll();
        Employee firstEmployee = employeeList.get(0);
        String startDate = firstEmployee.getRegisteredAt();
        Collection<String> dates = new ArrayList<>();

        String inputDateStr = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate inputDate = LocalDate.parse(inputDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate nextDay;
        String nextDayString = "";
        Integer counter = 0;
        dates.add(startDate);

        do {
            counter++;
            nextDay = inputDate.plusDays(counter);
            nextDayString = nextDay.format(DateTimeFormatter.ISO_LOCAL_DATE);
            dates.add(nextDayString + "-00-00-00");
        }
        while (!nextDayString.equals(today));

        Collection<Employee> employees = new ArrayList<>();
        employees.addAll(employeeRepository.findAll());

        Collection<Object> fullData = new ArrayList<>();
        dates.forEach((date -> {
            Collection<Object> timestampVsValue = new ArrayList<>();
            timestampVsValue.add(getTimestamp(date));
            addValue(timestampVsValue, dates);

            int employeeCounter = 0;

            for (Employee employee : allEmployees) {
                if (employee.getRegisteredAt().substring(0, 10).equals(date.substring(0, 10))) {
                    employeeCounter++;
                }
            }

            timestampVsValue.add(employeeCounter);
            fullData.add(timestampVsValue);
        }));


        return new ResponseEntity<> (fullData, HttpStatus.OK);
    }


    void addValue(Collection<Object> timestampVsValue, Collection<String> dates) {

    }

    Long getTimestamp(String registeredAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime localDateTime = LocalDateTime.parse(registeredAt, formatter);


        // Extract individual components
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        LocalDateTime dateTime = LocalDateTime.of(year, Month.of(month), day, hour, minute, second);
        Instant instant = dateTime.toInstant(ZoneOffset.UTC);

        return instant.toEpochMilli();
    }
}
