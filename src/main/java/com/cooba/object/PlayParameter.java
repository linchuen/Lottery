package com.cooba.object;

import lombok.Builder;
import lombok.Getter;
import com.cooba.enums.ColorEnum;

@Getter
@Builder
public class PlayParameter {
    private Integer position;
    private Boolean isBig;
    private Boolean isOdd;
    private ColorEnum color;
}
