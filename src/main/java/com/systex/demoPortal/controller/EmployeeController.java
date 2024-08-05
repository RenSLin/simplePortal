package com.systex.demoPortal.controller;

import com.systex.demoPortal.model.Employee;
import com.systex.demoPortal.model.EmployeeRepository;
import com.systex.demoPortal.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController{

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/")
    public String portal(Model model, HttpSession session){
        model.addAttribute("error",session.getAttribute("error"));
        model.addAttribute("employee", new Employee());
        return "portal";
    }

    @PostMapping("/login")
    public String getEmployee(@ModelAttribute Employee employee, Model model, HttpSession session){

        System.out.println("Inside getEmployee Controller");
        Employee emp = (Employee) session.getAttribute("authorizedEmp");
        if (emp == null) {
            return "portal";
        }
        System.out.println(emp);
        model.addAttribute("employee",emp);
        return "/employee";

    }

    @GetMapping("/logout")
    public String logOut(Model model, HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
