package com.kesi.planit.core;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CommonResult {
    private int code;
    private String msg;
    private boolean success;
}
