package com.gova.EasyGuide.configurations;

import com.gova.EasyGuide.service.db1.Users.JWTService;
import com.gova.EasyGuide.service.db1.Users.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//for every rewquest the filter need to be executed once
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader =request.getHeader("Authorization");
        String token=null;
        String username=null;

        if(authHeader !=null && authHeader.startsWith("Bearer "))
        {
            token=authHeader.substring(7);
            username=jwtService.extractUserName(token);

        }

        if(username !=null && SecurityContextHolder.getContext().getAuthentication() ==null)
        {
            UserDetails userDetails = (UserDetails) applicationContext.getBean(UserService.class).getUserDetails(username);
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authtoken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
