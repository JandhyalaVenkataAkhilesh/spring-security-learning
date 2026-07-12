package com.example.module5.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class LoggingFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Request Header : {}",request.getHeader("Authorization"));
        log.info("Request Method is : {}",request.getMethod());
        log.info("Request Location is {}: ",request.getRequestURI());
        filterChain.doFilter(request,response);
        log.info("Response is : {}",response.getStatus());
    }
}
