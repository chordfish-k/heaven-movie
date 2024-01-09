package com.chord.lsr.task;

import com.chord.lsr.properties.RankItemProperties;
import com.chord.lsr.properties.RankProperties;
import com.chord.lsr.service.MovieRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
@PropertySource(value = "classpath:application.yml",encoding = "UTF-8")
public class RankTask {
    @Autowired
    private MovieRankService movieRankService;
    @Autowired
    private RankProperties rankProperties;

    @Scheduled(cron = "${haven-movie.rank.cron}")
    public void rankDaily() {
        log.info("重新计算排行...");
        List<RankItemProperties> rankList = rankProperties.getRankList();
        rankList.forEach(rankItemProperties -> {
            movieRankService.getRankById(rankItemProperties.getRankId().getValue());
        });
    }
}
