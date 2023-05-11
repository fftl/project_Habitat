package com.ssafy.habitat.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TestRedis {
    @Id
    private String userKey;
    private String nickname;
}
