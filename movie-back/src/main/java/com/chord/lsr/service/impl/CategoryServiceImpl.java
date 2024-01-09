package com.chord.lsr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chord.lsr.mapper.CategoryMapper;
import com.chord.lsr.mapper.CategoryTypeMapper;
import com.chord.lsr.pojo.entity.Category;
import com.chord.lsr.pojo.entity.CategoryType;
import com.chord.lsr.pojo.vo.CategoryVO;
import com.chord.lsr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryTypeMapper categoryTypeMapper;

    public CategoryVO list(Integer type) {
        QueryWrapper<Category> qw = new QueryWrapper<>();
        qw.eq("type_id", type);
        // 查询该类型名字
        CategoryType categoryType = categoryTypeMapper.selectById(type);
        // 查询该type下所有tag
        List<Category> categories = categoryMapper.selectList(qw);
        List<String> tagList = new ArrayList<>();

        categories.forEach((category -> {
            tagList.add(category.getTagName());
        }));
        CategoryVO categoryVO = CategoryVO.builder()
                .typeName(categoryType.getName())
                .tags(tagList)
                .build();
        return categoryVO;
    }
}
