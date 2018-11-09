package com.baidubce.iot.duhome.demo.util;

import com.baidubce.iot.duhome.demo.Application;
import com.baidubce.iot.duhome.demo.demo_use_only.RedisHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisHelperTest {
    @Autowired
    RedisHelper redisHelper;

    @Test
    public void testRedisSetGet() {
        redisHelper.set("hello", new Hello("world"));
        Hello hello = (Hello) redisHelper.get("hello");
        assertEquals("world", hello.getName());
    }

    @Test
    public void testDouble() {
        redisHelper.set("double", 10D);
        double d = (double)redisHelper.get("double");
        System.out.println(d);
        assertEquals(10D, d, 0.01);
    }
}
