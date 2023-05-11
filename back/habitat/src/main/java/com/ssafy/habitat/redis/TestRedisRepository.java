package com.ssafy.habitat.redis;

import org.springframework.data.repository.CrudRepository;

public interface TestRedisRepository extends CrudRepository<TestRedis, String> {
}
