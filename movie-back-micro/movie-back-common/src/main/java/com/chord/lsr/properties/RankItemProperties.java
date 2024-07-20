package com.chord.lsr.properties;

import com.chord.lsr.enums.RankId;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RankItemProperties {
    RankId rankId;
    Integer count;
}