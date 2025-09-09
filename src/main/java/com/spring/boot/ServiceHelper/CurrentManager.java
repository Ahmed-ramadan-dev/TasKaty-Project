package com.spring.boot.ServiceHelper;

import com.spring.boot.BundleMessage.Exception.SystemException;
import com.spring.boot.Model.User;
import com.spring.boot.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CurrentManager {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CurrentUserService currentUserService;
    public  User getCurrentManager() {
        return userRepo.findById(currentUserService.CurrentUser().getId())
                .orElseThrow(() -> new SystemException("manager.not.found", HttpStatus.NOT_FOUND));
    }

}
