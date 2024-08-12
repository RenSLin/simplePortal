package com.systex.demoPortal.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systex.demoPortal.model.Employee;
import com.systex.demoPortal.service.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private EmployeeService employeeService;

    private final Set<String> EXCLUDED_URL = new HashSet<>(Set.of("/","/ajax-portal","/logout"));


    protected boolean isExcluded(String url) {
        return EXCLUDED_URL.contains(url) || url.startsWith("/js/") || url.startsWith("/style/") || url.startsWith("/img/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI();
        logger.info("Getting URL: " + url);
        if(isExcluded(url)) {
            logger.info("exclusion url: " + url);
            filterChain.doFilter(request, response);
            return;
        }

        if ("/ajaxlogin".equals(url)){
            doAjax(request, response, filterChain);
        } else if ("/login".equals(url)) {
            doForm(request,response, filterChain);
        } else if ("/employee".equals(url)) {
            if (request.getSession().getAttribute("authorizedEmp") == null) {
                response.sendRedirect("/");
                return;
            }
            filterChain.doFilter(request,response);
        } else {
            response.sendRedirect("/");
        }
    }

    //ajax submission logic for filter
    protected void doAjax(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> ajaxVal = objectMapper.readValue(request.getInputStream(), Map.class);
        Integer uid = Integer.parseInt(ajaxVal.get("id"));
        String password = ajaxVal.get("password");
        session.setAttribute("userID", uid);

        Map<String, String> errorResponse = new HashMap<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            Employee emp = employeeService.getEmployeeById(uid);
            if (emp.getPassword().equals(password)){
                logger.info("correct everything filter");
                session.setAttribute("authorizedEmp", emp);
                filterChain.doFilter(request, response);
            } else  {
                logger.info("incorrect password filter");
                errorResponse.put("error","Invalid Password");
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            }
        } catch (RuntimeException e) {
            logger.info("incorrect ID filter");
            errorResponse.put("error","Invalid ID");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }

    //form submission logic for filter
    protected void doForm(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
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


