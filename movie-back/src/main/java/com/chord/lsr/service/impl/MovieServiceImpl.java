package com.chord.lsr.service.impl;

import com.chord.lsr.constant.MessageConstant;
import com.chord.lsr.constant.RankIdConstant;
import com.chord.lsr.constant.SortIdConstant;
import com.chord.lsr.exception.NoSuchMovieException;
import com.chord.lsr.mapper.MovieLikeMapper;
import com.chord.lsr.mapper.MovieMapper;
import com.chord.lsr.pojo.dto.MovieQueryDTO;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.result.PageResult;
import com.chord.lsr.service.MovieService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieMapper movieMapper;

    public Movie getById(Long id) {
        Movie movie = movieMapper.selectById(id);
        if (movie == null) {
            throw new NoSuchMovieException(MessageConstant.NO_MOVIE);
        }

        return movie;
    }

    public PageResult queryPage(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Movie> page = movieMapper.queryPage();
        return new PageResult(page.getTotal(), page.getResult());
    }

    public PageResult query(MovieQueryDTO movieQueryDTO) {
        PageHelper.startPage(movieQueryDTO.getPage(), movieQueryDTO.getPageSize());
        // 排序设置
        if (Objects.equals(movieQueryDTO.getSortId(), SortIdConstant.BY_TIME)) {
            PageHelper.orderBy("release_time desc");
        } else if (Objects.equals(movieQueryDTO.getSortId(), SortIdConstant.BY_SCORE)) {
            PageHelper.orderBy("score desc, release_time desc");
        }

        List<Movie> list;
        Function<MovieQueryDTO, List<Movie>> query;
        if (Objects.equals(movieQueryDTO.getShowType(), RankIdConstant.BY_HOT))
            query = movieMapper::queryOnHot;
        else
            query = movieMapper::query;
        list = query.apply(movieQueryDTO);

        PageInfo<Movie> page = new PageInfo<>(list);
        return new PageResult(page.getTotal(), page.getList());
    }


    public void updateById(Movie movie) {
        movieMapper.updateById(movie);
    }

}
