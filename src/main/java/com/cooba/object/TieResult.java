package com.cooba.object;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TieResult extends PlayResult {
    private boolean isTie = true;
}
