package com.chord.lsr.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "haven-movie.media")
public class MediaProperties {
    public String url;
    public String imagePath;
    public String videoPath;
}
