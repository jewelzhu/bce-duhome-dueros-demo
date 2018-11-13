package com.baidubce.iot.dueros.bot.demo_use_only;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
@ConditionalOnExpression("${use.mock.user.appliance.manager:false}")
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
