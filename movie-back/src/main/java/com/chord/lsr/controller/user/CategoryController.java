package com.chord.lsr.controller.user;

import com.chord.lsr.pojo.entity.Category;
import com.chord.lsr.pojo.result.Result;
import com.chord.lsr.pojo.vo.CategoryVO;
import com.chord.lsr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result<CategoryVO> list(Integer type) {
        CategoryVO categoryVO = categoryService.list(type);
        return Result.success(categoryVO);
    }
}
