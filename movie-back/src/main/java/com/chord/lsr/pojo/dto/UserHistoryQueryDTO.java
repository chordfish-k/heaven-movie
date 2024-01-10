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
public class UserHistoryQueryDTO implements Serializable {
    private Integer page;
    private Integer pageSize;
}
