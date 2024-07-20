package com.chord.lsr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chord.lsr.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
