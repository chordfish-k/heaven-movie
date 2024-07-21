package com.chord.lsr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients
@ConfigurationPropertiesScan("com.chord.lsr.properties")
public class MovieUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieUserApplication.class, args);
    }

}
