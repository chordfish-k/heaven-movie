package com.chord.lsr.controller.user;

import com.chord.lsr.pojo.dto.MovieQueryDTO;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.result.PageResult;
import com.chord.lsr.pojo.result.Result;
import com.chord.lsr.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/movie")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * 根据id查询电影
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Movie> getById(@PathVariable Long id) {
        Movie movie = movieService.getById(id);
        return Result.success(movie);
    }

    /**
     * 分页查询电影
     * @param pageSize
     * @param currentPage
     * @return
     */
    @GetMapping("/page/{pageSize}/{currentPage}")
    public Result<PageResult> queryPage(@PathVariable Integer pageSize, @PathVariable Integer currentPage) {
        PageResult page = movieService.queryPage(currentPage, pageSize);
        return Result.success(page);
    }


    /**
     * 条件查询电影
     */
    @GetMapping
    public Result<PageResult> query(MovieQueryDTO movieQueryDTO) {
        log.info("查询电影：{}", movieQueryDTO);
        PageResult page = movieService.query(movieQueryDTO);
        return Result.success(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public Result view(@PathVariable Long id) {
        // 该视频观看次数+1
        Movie movie = movieService.getById(id);
        Movie movieUpdated = Movie.builder()
                .id(id)
                .views(movie.getViews() + 1)
                .build();
        movieService.updateById(movieUpdated);
        return Result.success();
    }

}
