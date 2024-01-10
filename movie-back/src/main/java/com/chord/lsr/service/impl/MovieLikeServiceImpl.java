package com.chord.lsr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chord.lsr.context.UserContext;
import com.chord.lsr.mapper.MovieLikeMapper;
import com.chord.lsr.mapper.MovieMapper;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.entity.MovieLike;
import com.chord.lsr.service.MovieLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieLikeServiceImpl implements MovieLikeService {
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieLikeMapper movieLikeMapper;

    public void likeReverse(Long movieId) {
        Long userId = UserContext.getCurrentId();
        QueryWrapper<MovieLike> qw = new QueryWrapper<>();
        qw.eq("movie_id", movieId)
                .eq("user_id", userId)
                .select("id, like_flag");
        MovieLike like = movieLikeMapper.selectOne(qw);
        Integer add = 0;
        if (like == null) {
            like = MovieLike.builder()
                    .likeFlag(1)
                    .movieId(movieId)
                    .userId(userId)
                    .build();
            movieLikeMapper.insert(like);
            add = 1;
        } else {
            Integer v = like.getLikeFlag();
            like.setLikeFlag(1-v);
            movieLikeMapper.updateById(like);
            add = v == 1 ?  -1 : 1;
        }
        // 更新movie信息
        Movie movie = movieMapper.selectById(movieId);
        movie.setLikes(movie.getLikes() + add);
        movieMapper.updateById(movie);
    }

    public Integer isLike(Long movieId) {
        Long userId = UserContext.getCurrentId();
        QueryWrapper<MovieLike> qw = new QueryWrapper<>();
        qw.eq("movie_id", movieId)
                .eq("user_id", userId)
                .select("id, like_flag");
        MovieLike like = movieLikeMapper.selectOne(qw);
        if (like != null)
            return like.getLikeFlag();
        return 0;
    }
}
