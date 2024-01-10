package com.chord.lsr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chord.lsr.pojo.entity.UserHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserHistoryMapper extends BaseMapper<UserHistory> {
    List<Long> getHistory(Long userId);
}
