package com.chord.lsr.service;

import com.chord.lsr.pojo.vo.RankItemVO;

import java.util.List;

public interface RankService {
    /**
     * 查询排行
     * @param rankId
     * @param count
     * @return
     */
    List<RankItemVO> rankBy(Integer rankId, Integer count);
}
