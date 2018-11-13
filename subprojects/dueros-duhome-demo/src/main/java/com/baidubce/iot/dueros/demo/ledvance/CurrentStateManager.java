package com.baidubce.iot.dueros.demo.ledvance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

/**
 * 因为ledvance灯没有在duhome云端存状态，所以demo里才需要用redis存，否则直接从duhome接口读即可
 */
@Service
@Slf4j
@ConditionalOnExpression("${demo.ledvance:false}")
public class CurrentStateManager {
    @Autowired
    RedisHelper redisHelper;

    private static final String BRIGHTNESS_PERCENTAGE = "brightness-percentage-";

    private static final String TEMPERATURE_PERCENTAGE = "temperature-";

    public int getCurrentBrightness(String puid) {
        Object o = redisHelper.get(brightnessRedisKey(puid));
        if (o == null) {
            return 0;
        }
        else {
            return (int) o;
        }
    }

    public void saveCurrentBrightness(String puid, int brightness) {
        log.debug("save current brightness {} {}", puid, brightness);
        redisHelper.set(brightnessRedisKey(puid), brightness);
    }

    public int getCurrentTemperature(String puid) {
        Object o = redisHelper.get(temperatureRedisKey(puid));
        if (o == null) {
            return 0;
        }
        else {
            return (int) o;
        }
    }

    public void saveCurrentTemperature(String puid, int value) {
        log.debug("save current temperature {} {}", puid, value);
        redisHelper.set(temperatureRedisKey(puid), value);
    }

    public String brightnessRedisKey(String puid) {
        return BRIGHTNESS_PERCENTAGE + puid;
    }

    public String temperatureRedisKey(String puid) {
        return TEMPERATURE_PERCENTAGE + puid;
    }


}
