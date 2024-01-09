package com.chord.lsr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.chord.lsr.properties")
public class MovieBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieBackApplication.class, args);
    }

}
