package com.sc.security;

import javax.servlet.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


import java.io.IOException;

public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("Before authentication: " + httpRequest.getRequestURI());
        chain.doFilter(request, response);
    }


}