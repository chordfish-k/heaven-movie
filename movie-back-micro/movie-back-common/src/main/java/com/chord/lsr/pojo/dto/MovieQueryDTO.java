package com.chord.lsr.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieQueryDTO implements Serializable {
    private Integer showType;
    private Integer sortId;
    private String name;
    private Integer catId;
    private Integer sourceId;
    private Integer yearId;
    private String type;
    private String zoom;
    private String releaseTime;
    private String yearBegin;
    private String yearEnd;
    private String keyword;
    private String staff;
    private Integer vip;
    private Integer page;
    private Integer pageSize;
}
