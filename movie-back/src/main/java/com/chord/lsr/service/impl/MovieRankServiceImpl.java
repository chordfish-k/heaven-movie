package com.chord.lsr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chord.lsr.constant.RankIdConstant;
import com.chord.lsr.enums.RankId;
import com.chord.lsr.mapper.MovieMapper;
import com.chord.lsr.mapper.MovieRankMapper;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.entity.MovieRank;
import com.chord.lsr.pojo.vo.RankItemVO;
import com.chord.lsr.properties.RankItemProperties;
import com.chord.lsr.properties.RankProperties;
import com.chord.lsr.service.MovieRankService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieRankServiceImpl extends ServiceImpl<MovieRankMapper, MovieRank>  implements MovieRankService {
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RankProperties rankProperties;

    public List<RankItemVO> getRankById(Integer rankId) {
        QueryWrapper<Movie> qw = new QueryWrapper<>();
        RankId rankId1 = RankId.getByValue(rankId);
        switch (rankId1) {
            case BY_SCORE:
                qw.orderByDesc("score");
                break;
            case BY_HOT:
                qw.orderByDesc("views");
                break;
            case BY_LIKE:
                qw.orderByDesc("likes");
                break;
            case BY_COMP:
                qw.orderByAsc("rv");
                break;
        }

        // 获取排行的条数
        Integer count = 10;
        for (RankItemProperties ri : rankProperties.getRankList()) {
            if (ri.getRankId().equals(rankId1)) {
                count = ri.getCount();
                break;
            }
        }
        qw.last("limit "+count);

        List<Movie> list = movieMapper.selectList(qw);

        List<RankItemVO> rankItemVOList = list.stream().map(movie -> {
            RankItemVO rankItemVO = new RankItemVO();
            BeanUtils.copyProperties(movie, rankItemVO);
            rankItemVO.setMovieId(movie.getId());

            String value = "";
            // 排行榜值的选取
            switch (rankId1) {
                case BY_SCORE:
                    value = movie.getScore();
                    break;
                case BY_HOT:
                    value = movie.getViews().toString();
                    break;
                case BY_LIKE:
                    value = movie.getLikes().toString();
                    break;
                case BY_COMP:
                    value = movie.getScore() + "," + movie.getEvalNum();
                    break;
            }

            rankItemVO.setRankValue(value);

            return rankItemVO;
        }).collect(Collectors.toList());
        // 存到redis
        redisTemplate.opsForValue().set("rankCache::"+rankId, rankItemVOList);
        return rankItemVOList;
    }
}
