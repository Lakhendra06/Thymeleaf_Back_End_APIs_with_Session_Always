package com.tms.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.util.logging.Logger;

public class AuthoritiesLoggingAfterFilter implements Filter{

    private final Logger LOG = Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null != authentication){
            LOG.info("User " + authentication.getName() + " is successfullty authenticated and " + "has the authorities " + authentication.getAuthorities().toString());
        }
        arg2.doFilter(arg0, arg1);
    }
    
}
