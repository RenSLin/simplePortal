package com.systex.demoPortal.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systex.demoPortal.model.Employee;
import com.systex.demoPortal.service.EmployeeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI();
        if ("/ajaxlogin".equals(url)){
            ajaxSubmissionFilter(request, response, filterChain);
            return;
        }
        if ("/login".equals(url)) {
            formSubmissionFilter(request,response, filterChain);
            return;
        }
        logger.info("leaving filter");
        filterChain.doFilter(request, response);
    }

    //ajax submission logic for filter
    protected void ajaxSubmissionFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> ajaxVal = objectMapper.readValue(request.getInputStream(), Map.class);
        String id = ajaxVal.get("id");
        Integer uid = Integer.parseInt(id);
        String password = ajaxVal.get("password");
        session.setAttribute("userID", uid);
        try {
            Employee emp = employeeService.getEmployeeById(uid);
            System.out.println("Here is the emp" + emp);
            if (!emp.getPassword().equals(password)) {
                session.setAttribute("error", "Incorrect Password");
                logger.info("incorrect password filter");
                response.sendRedirect("/ajax-portal");
                return;
            }
            logger.info("correct everything filter");
            session.setAttribute("authorizedEmp", emp);
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            session.setAttribute("error", "Invalid ID");
            logger.info("incorrect ID filter");
            response.sendRedirect("/ajax-portal");
        }
    }

    //form submission logic for filter
    protected void formSubmissionFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        HttpSession session = request.getSession();
        try {
            String id = request.getParameter("id");
            String password = request.getParameter("password");
            Integer uid = Integer.parseInt(id);
            Employee emp = employeeService.getEmployeeById(uid);
            System.out.println("Here is the emp" + emp);
            if (!emp.getPassword().equals(password)) {
                session.setAttribute("error", "Incorrect Password");
                logger.info("incorrect password filter");
                response.sendRedirect("/");
                return;
            }
            //if everything goes well
            logger.info("correct everything filter");
            session.setAttribute("authorizedEmp", emp);
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            session.setAttribute("error", "Invalid ID");
            logger.info("incorrect ID filter");
            response.sendRedirect("/");
        }
    }
}


