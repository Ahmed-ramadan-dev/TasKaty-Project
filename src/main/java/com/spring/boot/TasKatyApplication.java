package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class TasKatyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasKatyApplication.class, args);
    }

}
