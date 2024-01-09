package com.chord.lsr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chord.lsr.pojo.dto.MovieQueryDTO;
import com.chord.lsr.pojo.entity.Movie;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper extends BaseMapper<Movie> {
    /**
     * 分页查询所有电影
     * @return
     */
    Page<Movie> queryPage();

    /**
     * 查询所有电影
     * @return
     */
    List<Movie> queryAll();

    List<Movie> queryOnHot(MovieQueryDTO movieQueryDTO);

    List<Movie> query(MovieQueryDTO movieQueryDTO);
}
