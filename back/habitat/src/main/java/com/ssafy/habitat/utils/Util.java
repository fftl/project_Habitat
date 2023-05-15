package com.ssafy.habitat.utils;

import com.ssafy.habitat.config.TokenProvider;
import com.ssafy.habitat.websocket.WebSocketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.IntStream;

@Component
public class Util {
    private final Logger LOGGER = LoggerFactory.getLogger(Util.class);


    private TokenProvider tokenProvider;

    public String createKey(int len){
        int leftLimit = 48; // 숫자의 0~9의 아스키코드 48-57
        int rightLimit = 122; // 소문자 영어의 아스키코드 97-122
        int targetStringLength = len; // 생성하고자 하는 문자열 길이

        Random random = new Random();
        String key = random.ints(leftLimit, rightLimit + 1)

                /**
                filter의 시작과 종료는 leftLimit, rightLimit으로 표현하여
                숫자의 아스키코드인 48~57, 대문자의 아스키코드 65~90, 소문자의 아스키코드 97~122를 필터로 지정해주었습니다.
                 */
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return key;
    }
}
