package com.systex.demoPortal.controller;

import com.systex.demoPortal.model.Employee;
import com.systex.demoPortal.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController{

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String portal(Model model){
        model.addAttribute("employee", new Employee());
        return "portal";
    }

    @PostMapping("/login")
    public String getEmployee(@ModelAttribute Employee employee, Model model){
        employeeRepository.save(employee);
        model.addAttribute("employee",employee);
        return "employee";
    }

}
