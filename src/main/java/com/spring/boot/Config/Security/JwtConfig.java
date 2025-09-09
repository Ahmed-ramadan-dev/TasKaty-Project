package com.spring.boot.Config.Security;
import com.spring.boot.Help.JwtToken;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
public class JwtConfig {

    private final JwtToken jwtToken;

    public JwtConfig(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Bean
    public JwtParser jwtParser() {
        Key key = Keys.hmacShaKeyFor(jwtToken.getSecret().getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }
}
