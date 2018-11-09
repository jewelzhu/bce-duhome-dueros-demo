package com.baidubce.iot.duhome.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
public class RedisHelper {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void set(String key, Serializable value) {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(key, value);
    }

    public Object get(String key) {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        return vop.get(key);
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
