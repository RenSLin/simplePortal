package com.systex.demoPortal.service;

import com.systex.demoPortal.model.Employee;

import java.util.List;

public interface DemoService {
    List<Employee> getAllEmployee();
    Employee getEmployeeById(Integer id);

}
