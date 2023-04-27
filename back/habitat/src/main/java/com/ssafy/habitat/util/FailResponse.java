package com.ssafy.habitat.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FailResponse {
    String msg;
    int code;
}
