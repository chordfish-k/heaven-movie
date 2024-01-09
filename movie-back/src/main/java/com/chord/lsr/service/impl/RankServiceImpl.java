package com.chord.lsr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chord.lsr.constant.RankIdConstant;
import com.chord.lsr.mapper.MovieMapper;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.vo.RankItemVO;
import com.chord.lsr.service.RankService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RankServiceImpl implements RankService {
    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<RankItemVO> rankBy(Integer rankId, Integer count) {
        QueryWrapper<Movie> qw = new QueryWrapper<>();
        if (Objects.equals(rankId, RankIdConstant.BY_SCORE)) {
            qw.orderByDesc("score");
        }
        else if (Objects.equals(rankId, RankIdConstant.BY_HOT)) {
            qw.orderByDesc("views");
        }
        else if (Objects.equals(rankId, RankIdConstant.BY_LIKE)) {
            qw.orderByDesc("likes");
        }
        else if (Objects.equals(rankId, RankIdConstant.BY_COMP)) {
            qw.orderByAsc("rv");
        }
        qw.last("limit "+count);

        List<Movie> list = movieMapper.selectList(qw);

        List<RankItemVO> rankItemVOList = list.stream().map(movie -> {
            RankItemVO rankItemVO = new RankItemVO();
            BeanUtils.copyProperties(movie, rankItemVO);
            rankItemVO.setMovieId(movie.getId());

            String value = "";
            // 排行榜值的选取
            if (Objects.equals(rankId, RankIdConstant.BY_SCORE)) {
                value = movie.getScore();
            }
            else if (Objects.equals(rankId, RankIdConstant.BY_HOT)) {
                value = movie.getViews().toString();
            }
            else if (Objects.equals(rankId, RankIdConstant.BY_LIKE)) {
                value = movie.getLikes().toString();
            }
            else if (Objects.equals(rankId, RankIdConstant.BY_COMP)) {
                value = movie.getScore() + "," + movie.getEvalNum();
            }

            rankItemVO.setRankValue(value);

            return rankItemVO;
        }).collect(Collectors.toList());

        return rankItemVOList;
    }
}
