package com.systex.demoPortal.service;

import com.systex.demoPortal.model.Employee;
import com.systex.demoPortal.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements DemoService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Optional<Employee> emp = employeeRepository.findById(id);
        Employee employee = null;
        if (emp.isPresent()) {
            employee = emp.get();
        } else {
            throw new RuntimeException(
                    "Employee not found for id : " + id);
        }
        return employee;
    }

}
