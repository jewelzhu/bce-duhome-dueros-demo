package com.baidubce.iot.duhome.demo.duhome.executor;

import com.baidubce.iot.duhome.demo.dueros.model.Percentage;
import com.baidubce.iot.duhome.demo.util.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 因为ledvance灯没有在duhome云端存状态，所以demo里才需要用redis存，否则直接从duhome接口读即可
 */
@Service
@Slf4j
public class CurrentStateManager {
    @Autowired
    RedisHelper redisHelper;

    private static final String BRIGHTNESS_PERCENTAGE = "brightness-percentage-";

    public Percentage getCurrentBrightness(String puid) {
        return   (Percentage) redisHelper.get(brightnessRedisKey(puid));
    }

    public void saveCurrentBrightness(String puid, Percentage brightness) {
        log.debug("save current brightness {} {}", puid, brightness);
        redisHelper.set(brightnessRedisKey(puid), brightness);
    }

    public String brightnessRedisKey(String puid) {
        return BRIGHTNESS_PERCENTAGE + puid;
    }
}
