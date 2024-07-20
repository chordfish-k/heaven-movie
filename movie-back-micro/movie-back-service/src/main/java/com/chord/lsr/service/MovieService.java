package com.chord.lsr.service;

import com.chord.lsr.pojo.dto.MovieQueryDTO;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.result.PageResult;

public interface MovieService {
    /**
     * 根据id查询电影
     *
     * @param id
     * @return
     */
    Movie getById(Long id);

    /**
     * 分页查询电影
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageResult queryPage(Integer currentPage, Integer pageSize);

    /**
     * 条件查询
     *
     * @param movieQueryDTO
     * @return
     */
    PageResult query(MovieQueryDTO movieQueryDTO);

    /**
     * 根据id更新信息
     * @param movie
     */
    void updateById(Movie movie);

}
