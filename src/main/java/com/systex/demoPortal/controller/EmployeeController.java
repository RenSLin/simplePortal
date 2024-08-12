package com.systex.demoPortal.controller;

import com.systex.demoPortal.model.Employee;
import com.systex.demoPortal.model.EmployeeRepository;
import com.systex.demoPortal.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class EmployeeController{

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/")
    public String portal(Model model, HttpSession session){
        model.addAttribute("error",session.getAttribute("error"));
        model.addAttribute("employee", new Employee());
        session.invalidate();
        return "portal";
    }

    @PostMapping("/login")
    public String getEmployee(@ModelAttribute Employee employee, Model model, HttpSession session){
        System.out.println("Inside  Controller");
        Employee emp = (Employee) session.getAttribute("authorizedEmp");
        model.addAttribute("employee",emp);
        return "employee";
    }


    @PostMapping("/ajaxlogin")
    @ResponseBody
    public String ajaxGetEmployee(HttpSession session) {
        System.out.println("Inside AjaxGetEmployee Controller");
        Employee emp = (Employee) session.getAttribute("authorizedEmp");
        return "success";
    }


    @GetMapping("/logout")
    public String logOut(Model model, HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/ajax-portal")
    public String ajaxPortal(Model model, HttpSession session) {
        model.addAttribute("error", session.getAttribute("error"));
        model.addAttribute("employee", new Employee());
        session.invalidate();
        return "otherportal"; // the new AJAX login page
    }

    @GetMapping("/employee")
    public String employeePage(Model model, HttpSession session) {
        Employee emp = (Employee) session.getAttribute("authorizedEmp");
        model.addAttribute("employee", emp);
        return "employee";
    }

}
