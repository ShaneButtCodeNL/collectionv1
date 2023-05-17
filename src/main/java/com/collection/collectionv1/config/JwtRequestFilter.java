package com.collection.collectionv1.config;

import com.collection.collectionv1.services.JwtService;
import com.collection.collectionv1.services.JwtUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final String BEARER="Bearer ";
    @Autowired
    JwtService jwtService;
    @Autowired
    JwtUserDetailService jwtUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        boolean isValid=false;
        String token=null;
        //Token should start with "Bearer "
        if(requestTokenHeader!=null && requestTokenHeader.startsWith(BEARER)){
            //Remove "Bearer "
            token=requestTokenHeader.substring(BEARER.length());
            //Check if token is valid
            isValid=jwtService.validateToken(token);
        }
        if(isValid){
            UserDetails userDetails = jwtUserDetailService.loadUserByUsername(jwtService.getUsernameFromToken(token));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
