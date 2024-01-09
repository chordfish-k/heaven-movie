package com.chord.lsr.controller.user;

import com.chord.lsr.context.UserContext;
import com.chord.lsr.pojo.result.Result;
import com.chord.lsr.service.MovieLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/like")
public class MovieLikeController {
    @Autowired
    private MovieLikeService movieLikeService;

    @GetMapping("/{id}")
    private Result<Integer> isLike(@PathVariable Long id) {
        return Result.success(UserContext.getCurrentId() != null ? movieLikeService.isLike(id) : 0);
    }

    @PutMapping("/{id}")
    public Result like(@PathVariable Long id) {
        // 该视频观点赞数+1
        movieLikeService.likeReverse(id);
        return Result.success();
    }
}
