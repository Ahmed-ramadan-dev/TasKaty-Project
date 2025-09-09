package com.spring.boot.ServiceHelper;

import com.spring.boot.BundleMessage.Exception.SystemException;
import com.spring.boot.Dto.Security.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public UserResponseDto CurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            throw new SystemException("User.not.authenticated", HttpStatus.UNAUTHORIZED);
        }//user act login or no ?...
        UserResponseDto userResponseDto = (UserResponseDto) authentication.getPrincipal();
        return userResponseDto;

        }
    }
