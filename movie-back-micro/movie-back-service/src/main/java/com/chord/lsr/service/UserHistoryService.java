package com.chord.lsr.service;

import com.chord.lsr.pojo.dto.UserHistoryQueryDTO;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.result.PageResult;

import java.util.List;

public interface UserHistoryService {
    List<Movie> getHistory();
    void insertHistory(Long movieId);
}
