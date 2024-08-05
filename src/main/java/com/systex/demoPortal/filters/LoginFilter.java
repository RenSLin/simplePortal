package com.systex.demoPortal.filters;

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

@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI();
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if ("/login".equals(url) || "/employee".equals(url)) {
            try {
                Integer uid = Integer.parseInt(id);
                Employee emp = employeeService.getEmployeeById(uid);
                System.out.println("Here is the emp" + emp);
                if (!emp.getPassword().equals(password)) {
                    session.setAttribute("error", "Incorrect Password");
                    logger.info("incorrect password filter");
                    request.getRequestDispatcher("/").forward(request,response);
                    return;
                }
                logger.info("correct everything filter");
                session.setAttribute("authorizedEmp", emp);
            } catch (RuntimeException e) {
                session.setAttribute("error", "Invalid ID");
                logger.info("incorrect ID filter");
                request.getRequestDispatcher("/").forward(request,response);
                return;
            }
        }
        logger.info("leaving filter");
        filterChain.doFilter(request,response);
    }
}
