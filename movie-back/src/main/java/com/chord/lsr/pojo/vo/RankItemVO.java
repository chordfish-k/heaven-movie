package com.chord.lsr.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankItemVO implements Serializable {
    private Long movieId;
    private String name;
    private String releaseTime;
    private String staff;
    private String rankValue;
}
