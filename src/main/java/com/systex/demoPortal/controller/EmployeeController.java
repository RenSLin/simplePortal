package com.systex.demoPortal.controller;

import com.systex.demoPortal.model.Employee;
import com.systex.demoPortal.model.EmployeeRepository;
import com.systex.demoPortal.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class EmployeeController{

//    @Autowired
//    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/")
    public String portal(Model model){
        model.addAttribute("employee", new Employee());
        return "portal";
    }

    @PostMapping("/login")
    public String getEmployee(@ModelAttribute Employee employee, Model model){

//        Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());
//        if (existingEmployee.isEmpty()) {
//            model.addAttribute("error", "Invalid ID");
//            return "portal";
//        }
//        if(!existingEmployee.get().getPassword().equals(employee.getPassword())){
//            model.addAttribute("error", "Incorrect Password");
//            return "portal";
//        }
//        model.addAttribute("employee",existingEmployee.get());
//        return "employee";
        try {
            Employee emp = employeeService.getEmployeeById(employee.getId());
            if(!emp.getPassword().equals(employee.getPassword())){
                model.addAttribute("error", "Incorrect Password");
                return "portal";
            }
            model.addAttribute("employee",emp);
            return "employee";
        } catch(RuntimeException e){
            model.addAttribute("error", "Invalid ID");
            return "portal";
        }
    }



}
