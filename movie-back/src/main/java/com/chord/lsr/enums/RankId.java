package com.chord.lsr.enums;

import java.util.HashMap;
import java.util.Map;

public enum RankId {
    BY_SCORE(0),
    BY_HOT(1),
    BY_LIKE(2),
    BY_COMP(3);

    private Integer value;

    private static final Map<Integer, RankId> map = new HashMap<>();

    static {
        for (RankId rankId : values()) {
            map.put(rankId.value, rankId);
        }
    }
    RankId(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

    public static RankId getByValue(Integer value) {
        return map.get(value);
    }
}
