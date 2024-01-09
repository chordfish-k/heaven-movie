package com.chord.lsr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chord.lsr.pojo.entity.MovieLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieLikeMapper extends BaseMapper<MovieLike> {
    void setLike(Long movieId, Integer userId, Integer like);
}
