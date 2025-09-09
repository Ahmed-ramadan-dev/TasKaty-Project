package com.spring.boot.Config.Security;

import com.spring.boot.BundleMessage.Exception.SystemException;
import com.spring.boot.Dto.Security.RoleDto;
import com.spring.boot.Dto.Security.TokenUserDto;
import com.spring.boot.Dto.Security.UserResponseDto;
import com.spring.boot.Help.JwtToken;
import com.spring.boot.Mapper.UserMapper;
import com.spring.boot.Model.User;
import com.spring.boot.Repo.UserRepo;
import com.spring.boot.ServiceHelper.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;


@Component
public class TokenHandler {
    private final UserRepo userRepo;
    private final JwtParser jwtParser;
    private final Key key;
    private final JwtToken jwtToken;
    private final RefreshTokenService refreshTokenService;

    public TokenHandler(UserRepo userRepo , JwtToken jwtToken ,RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
        this.userRepo = userRepo;
        this.jwtToken = jwtToken;
        this.key = Keys.hmacShaKeyFor(jwtToken.getSecret().getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createAccessToken(TokenUserDto tokenUserDto) {
        return createToken(tokenUserDto,jwtToken.getAccessTime());
    }

    public String createRefreshToken(TokenUserDto tokenUserDto) {
        String refreshToken = createToken(tokenUserDto, jwtToken.getRefreshTime());
        User user = userRepo.findByEmail(tokenUserDto.getEmail())
                .orElseThrow(() -> new SystemException("email.not.found", HttpStatus.NOT_FOUND));
        user.setRefreshToken(refreshToken);
        userRepo.save(user);
        return refreshToken;
    }

    public String createToken(TokenUserDto tokenUserDto, Duration duration) {
        Date issueDate = new Date();
        Date expirationDate = Date.from(issueDate.toInstant().plus(duration));
        JwtBuilder jwtBuilder = Jwts.builder().signWith(key);

        jwtBuilder.setSubject(tokenUserDto.getEmail());
        jwtBuilder.setIssuedAt(issueDate);
        jwtBuilder.setExpiration(expirationDate);
        jwtBuilder.claim("Role",tokenUserDto.getRoleDto().getRoleName());
        jwtBuilder.claim("id",tokenUserDto.getUserId());

        return jwtBuilder.compact();
    }

    public UserResponseDto validateToken(String token) {
        if (!jwtParser.isSigned(token)) {
            throw new SystemException("token.invalid", HttpStatus.UNAUTHORIZED);
        }

        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        String email = claims.getSubject();
        Date issueDate = claims.getIssuedAt();
        Date expirationDate = claims.getExpiration();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new SystemException("user.not.found", HttpStatus.NOT_FOUND));

        if (expirationDate.before(new Date())) {
            throw new SystemException("token.expired", HttpStatus.UNAUTHORIZED);
        }
        if (!issueDate.before(expirationDate)) {
            throw new SystemException("token.issueDate.invalid", HttpStatus.BAD_REQUEST);
        }

        return UserMapper.USER_MAPPER.toUserResponseDto(user);
    }

    public String refreshAccessToken(String refreshToken) {
        User user = refreshTokenService.validateRefreshToken(refreshToken);

        TokenUserDto tokenUserDto = new TokenUserDto(
                user.getId(),
                user.getEmail(),
                new RoleDto(user.getRole().getRoleName())
        );

        return createAccessToken(tokenUserDto);
    }



    public void clearRefreshToken(String refreshToken) {
        User user = refreshTokenService.validateRefreshToken(refreshToken);
        refreshTokenService.clearRefreshToken(user);
    }

}
