package com.chord.lsr.client;

import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("movie-movie")
public interface MovieClient {

    /**
     * 根据id查询电影
     * @param id
     * @return
     */

    @GetMapping("/user/movie/y/{id}")
    Result<Movie> selectById(@PathVariable Long id);
}
