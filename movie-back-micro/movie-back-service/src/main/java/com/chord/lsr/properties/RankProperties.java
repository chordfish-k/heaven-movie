package com.chord.lsr.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "haven-movie.rank")
public class RankProperties {
    String cron;
    List<RankItemProperties> rankList;
}
