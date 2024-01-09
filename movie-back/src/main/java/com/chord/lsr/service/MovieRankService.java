package com.chord.lsr.service;

import com.chord.lsr.pojo.vo.RankItemVO;

import java.util.List;

public interface MovieRankService {
    List<RankItemVO> getRankById(Integer rankId);
}
