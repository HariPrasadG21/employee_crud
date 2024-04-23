package com.sc.security;


import com.sc.service.UserDetailsServiceimpl;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
   @Autowired
   JwtTokenHelper jwtTokenHelper;
   @Autowired
   UserDetailsServiceimpl userDetailsServiceimpl;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken = null;
        String username = null;

        // Get the auth_token cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth_token")) {
                    requestToken = cookie.getValue();
                    System.out.println("token12345 " + requestToken);
                    break;
                }
            }
        };
        if(requestToken!=null ){
              try{
                  username=this.jwtTokenHelper.getUsernameFromToken(requestToken);
              }
              catch(Exception ex){
                  throw new IllegalArgumentException("Unable to get JWT token");
              }

        }
        else{
           // System.out.println("token12345iii");
            logger.trace("No JWT token found in the request headers");
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= this.userDetailsServiceimpl.loadUserByUsername(username);

            if(this.jwtTokenHelper.validateToken(requestToken,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               // logger.info("Before setting authentication: " + SecurityContextHolder.getContext().getAuthentication());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                // Log after setting the authentication
                logger.info("After setting authentication: " + SecurityContextHolder.getContext().getAuthentication());
            }
            else {
                throw new IllegalArgumentException("Unable to validate JWT token");
            }
        }

        filterChain.doFilter(request,response);
    }

}
