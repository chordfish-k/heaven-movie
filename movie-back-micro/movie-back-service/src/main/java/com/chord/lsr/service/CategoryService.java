package com.chord.lsr.service;

import com.chord.lsr.pojo.vo.CategoryVO;

public interface CategoryService {
    /**
     * 根据类型id查询所有tag
     * @param type
     * @return
     */
   CategoryVO list(Integer type);
}
