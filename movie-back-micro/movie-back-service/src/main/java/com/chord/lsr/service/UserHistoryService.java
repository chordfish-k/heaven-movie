package com.chord.lsr.service;

import com.chord.lsr.pojo.entity.Movie;

import java.util.List;

public interface UserHistoryService {
    List<Movie> getHistory();
    void insertHistory(Long movieId);
}
