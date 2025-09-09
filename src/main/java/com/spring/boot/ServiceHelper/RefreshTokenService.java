package com.spring.boot.ServiceHelper;

import com.spring.boot.BundleMessage.Exception.SystemException;
import com.spring.boot.Model.User;
import com.spring.boot.Repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class RefreshTokenService {

    private final JwtParser jwtParser;
    private final UserRepo userRepo;

    public RefreshTokenService(JwtParser jwtParser, UserRepo userRepo) {
        this.jwtParser = jwtParser;
        this.userRepo = userRepo;
    }

  
    public User validateRefreshToken(String refreshToken) {
        if (!jwtParser.isSigned(refreshToken)) {
            throw new SystemException("refresh.token.invalid", HttpStatus.UNAUTHORIZED);
        }

        Claims claims = jwtParser.parseClaimsJws(refreshToken).getBody();

        String email = claims.getSubject();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new SystemException("user.not.found", HttpStatus.NOT_FOUND));

        if (user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
            throw new SystemException("refresh.token.mismatch", HttpStatus.UNAUTHORIZED);
        }

        if (claims.getExpiration().before(new Date())) {
            user.setRefreshToken(null);
            userRepo.save(user);
            throw new SystemException("refresh.token.expired", HttpStatus.UNAUTHORIZED);
        }

        return user;
    }

    
    public void clearRefreshToken(User user) {
        user.setRefreshToken(null);
        userRepo.save(user);
    }
}
