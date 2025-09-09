package com.spring.boot.Filters;

import com.spring.boot.Config.Security.TokenHandler;
import com.spring.boot.Config.SecurityConfig;
import com.spring.boot.Dto.Security.UserResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AuthFilters extends OncePerRequestFilter {
    @Autowired
    private TokenHandler tokenHandler;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        if (checkPath(path, SecurityConfig.PUBLIC_URLS)) {
            return true;
        }
        return false;
    }

    @SneakyThrows

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (Objects.isNull(token)|| !token.startsWith("Bearer "))  {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        token = token.substring(7);
        UserResponseDto userResponseDto = tokenHandler.validateToken(token);
        if(Objects.isNull(userResponseDto)) {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        GrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + userResponseDto.getRoleDto().getRoleName());
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userResponseDto, userResponseDto.getPassword(), List.of(role));

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);

    }// /auth/login
    private boolean checkPath(String originalPath,String [] publicPaths) {
        String [] parts = originalPath.split("/");// auth  login
        String finalPath = "/" + parts[1] + "/**"; // /auth/**
        return Arrays.asList(publicPaths).contains(finalPath);
    }//"/auth/**","/swagger-ui/**","/v3/**"

}

