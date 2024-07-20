package com.chord.lsr.controller.user;

import com.chord.lsr.context.UserContext;
import com.chord.lsr.mapper.UserHistoryMapper;
import com.chord.lsr.pojo.dto.UserHistoryQueryDTO;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.result.PageResult;
import com.chord.lsr.pojo.result.Result;
import com.chord.lsr.service.UserHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/history")
@Slf4j
public class UserHistoryController {
    @Autowired
    private UserHistoryService userHistoryService;

    /**
     * 查询历史记录
     * @return
     */
    @GetMapping
    public Result<List<Movie>> getHistory() {
        return Result.success(userHistoryService.getHistory());
    }
}
