package com.chord.lsr.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieLike implements Serializable {
    @TableId(type=IdType.AUTO)
    private Integer id;
    private Long movieId;
    private Integer userId;
    private Integer likeFlag;
}
