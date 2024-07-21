package com.chord.lsr.client;

import com.chord.lsr.pojo.entity.User;
import com.chord.lsr.pojo.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("movie-user")
public interface UserClient {

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    @GetMapping("/user/user/getById")
    Result<User> getById(@RequestParam Long id);

    /**
     * 插入历史记录
     * @param movieId
     * @return
     */
    @PostMapping("/user/history")
    Result insertHistory(@RequestParam Long userId, @RequestParam Long movieId);
}
