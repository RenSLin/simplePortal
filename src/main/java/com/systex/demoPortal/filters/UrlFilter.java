package com.systex.demoPortal.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class UrlFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getSession().getAttribute("authorizedEmp") == null) {
            System.out.println("inside filter Internal if statement");
            response.sendRedirect("/");
        }
        System.out.println("inside filter internal about to do filter");
        filterChain.doFilter(request,response);
    }
}
