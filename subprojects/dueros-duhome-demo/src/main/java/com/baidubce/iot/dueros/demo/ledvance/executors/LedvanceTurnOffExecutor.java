package com.baidubce.iot.dueros.demo.ledvance.executors;

import com.baidubce.iot.dueros.demo.ledvance.StaticUtil;
import com.baidubce.iot.dueros.bot.executor.light.TurnOffExecutor;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.Payload;
import com.baidubce.iot.dueros.bot.util.JsonHelper;
import com.baidubce.services.smarthome.SmarthomeAgentClient;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@ConditionalOnExpression("${demo.ledvance:false}")
public class LedvanceTurnOffExecutor implements TurnOffExecutor {
    @Autowired
    private SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway:a86htfpyzs7hnagd}")
    private String gateway;

    @Override
    public Payload executeCommand(String puid, Map<ExtraInfoKey, Object> extraInfo) {
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonHelper.toJsonNode("{\n" +
                    "      \"method\" : \"PUT\",\n" +
                    "      \"url\" : \"/v1/lights/" + StaticUtil.getRealLedvancePuid(puid) + "\",\n" +
                    "      \"body\" : \"{\\\"onOff\\\":0}\"\n" +
                    "    }");
        } catch (IOException e) {
            log.error("I will never reach here");
        }
        duhomeClient.updateAgentDeviceProfile(gateway, jsonNode);
        return new Payload();
    }
}
