package com.chord.lsr.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRank implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long movieId;
    private Integer rankIndex;
    private String rankValue;
    private LocalDate createDate;
}
