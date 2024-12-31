package com.kesi.planit.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CommonResult {
    private int code;
    private String msg;
    private boolean success;
}
