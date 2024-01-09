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
public class Movie implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String score;
    private String type;
    private String zoom;
    private String releaseTime;
    private String staff;
    private String duration;
    private String evalNum;
    private Integer views;
    private Integer likes;
    private String brief;
    private Integer vip;
}
