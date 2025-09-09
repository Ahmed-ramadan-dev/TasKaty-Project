package com.spring.boot.Help;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "token")
public class JwtToken {
    private Duration accessTime;
    private Duration refreshTime;
    private String secret;

}

