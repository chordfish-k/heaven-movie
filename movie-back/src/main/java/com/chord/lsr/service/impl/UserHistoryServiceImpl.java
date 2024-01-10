package com.chord.lsr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chord.lsr.context.UserContext;
import com.chord.lsr.mapper.MovieMapper;
import com.chord.lsr.mapper.UserHistoryMapper;
import com.chord.lsr.pojo.dto.UserHistoryQueryDTO;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.entity.UserHistory;
import com.chord.lsr.pojo.result.PageResult;
import com.chord.lsr.service.UserHistoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {
    @Autowired
    private UserHistoryMapper userHistoryMapper;

    @Autowired
    private MovieMapper movieMapper;

    public List<Movie> getHistory() {
        Long userId = UserContext.getCurrentId();
        // 在历史记录表中查出所有movieId并去重
        List<Long> historyIds = userHistoryMapper.getHistory(userId);
        if (historyIds == null || historyIds.size() == 0)
            return new ArrayList<>();
        // 根据id查出电影信息
        List<Movie> movieList = new ArrayList<>();
        historyIds.forEach(id->{
            movieList.add(movieMapper.selectById(id));
        });
        return movieList;
    }

    public void insertHistory(Long movieId) {
        Long userId = UserContext.getCurrentId();

        LambdaQueryWrapper<UserHistory> qw = new LambdaQueryWrapper<>();
        qw.eq(UserHistory::getUserId, userId)
                .eq(UserHistory::getMovieId, movieId);
        UserHistory userHistory = userHistoryMapper.selectOne(qw);

        if (userHistory == null) {
            // 插入新的历史记录
            UserHistory history = UserHistory.builder()
                    .userId(userId)
                    .movieId(movieId)
                    .timestamp(LocalDateTime.now())
                    .build();
            userHistoryMapper.insert(history);
        } else {
            // 修改记录的timestamp
            userHistory.setTimestamp(LocalDateTime.now());
            userHistoryMapper.updateById(userHistory);
        }
    }
}
