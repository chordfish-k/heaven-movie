package com.chord.lsr.controller.user;

import com.chord.lsr.pojo.result.Result;
import com.chord.lsr.pojo.vo.RankItemVO;
import com.chord.lsr.service.MovieRankService;
import com.chord.lsr.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/rank")
@Slf4j
public class MovieRankController {
    @Autowired
    private RankService rankService;
    @Autowired
    private MovieRankService movieRankService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public Result<List<RankItemVO>> rankBy(Integer rankId) {
        log.info("查询排行：{}", rankId);
        List<RankItemVO> rank = (List<RankItemVO>) redisTemplate.opsForValue().get("rankCache::"+rankId);
        if ( rank != null ) {
            return Result.success(rank);
        }
        List<RankItemVO> list = movieRankService.getRankById(rankId);
        return Result.success(list);
    }
}
