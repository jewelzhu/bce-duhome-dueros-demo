package com.baidubce.iot.dueros.demo.simpliest;

import com.baidubce.iot.dueros.bot.executor.light.TurnOnExecutor;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.Payload;
import com.baidubce.iot.dueros.bot.util.JsonHelper;
import com.baidubce.services.smarthome.SmarthomeClient;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@ConditionalOnExpression("${demo.simpliest:false}")
public class MyTurnOnExecutor implements TurnOnExecutor {
    @Autowired
    private SmarthomeClient duhomeClient;

    @Override
    public Payload executeCommand(String puid, Map<ExtraInfoKey, Object> extraInfo) {
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonHelper.toJsonNode("{\"power\":\"on\"}");
        } catch (IOException e) {
            log.error("I will never reach here");
        }
        duhomeClient.updatDeviceProfile(puid, jsonNode);
        return new Payload();
    }
}
