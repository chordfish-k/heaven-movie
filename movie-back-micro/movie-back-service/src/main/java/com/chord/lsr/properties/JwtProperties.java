package com.chord.lsr.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "haven-movie.jwt")
public class JwtProperties {
    private String SecretKey;
    private long Ttl;
    private String TokenName;

}
