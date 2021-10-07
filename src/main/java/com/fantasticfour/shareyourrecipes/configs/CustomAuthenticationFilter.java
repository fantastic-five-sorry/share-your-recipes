package com.fantasticfour.shareyourrecipes.configs;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
                password);
        return authenticationManager.authenticate(authentication);
    }

    // protected void successfulAuthentication(HttpServletRequest request,
    // HttpServletResponse response, FilterChain chain,
    // Authentication authResult) throws IOException, ServletException {
    // // User user = (User) authResult.getPrincipal();
    // // String accessToken = jwtUtils.generateAccessToken(user);
    // // String refreshToken = jwtUtils.generateRefreshToken(user);
    // // List<String> roles =
    // user.getAuthorities().stream().map(GrantedAuthority::toString)
    // // .collect(Collectors.toList());
    // // Map<String, Object> tokens = new HashMap<>();
    // // response.setStatus(HttpStatus.OK.value());
    // tokens.put("accessToken", accessToken);
    // tokens.put("refreshToken", refreshToken);
    // tokens.put("username", user.getUsername());
    // tokens.put("roles", roles);
    // response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    // new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    // }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("error", failed.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
