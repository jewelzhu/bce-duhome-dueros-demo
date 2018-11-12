package com.baidubce.iot.dueros.bot.custom.ledvance.executors;

import com.baidubce.iot.dueros.bot.executor.light.TurnOnExecutor;
import com.baidubce.iot.dueros.bot.model.CommandName;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.Payload;
import com.baidubce.services.smarthome.SmarthomeAgentClient;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static com.baidubce.iot.dueros.bot.demo_use_only.StaticUtil.getRealLedvancePuid;
import static com.baidubce.iot.dueros.bot.util.JsonHelper.toJsonNode;

@Component
@Slf4j
@ConditionalOnExpression("${use.mock.user.appliance.manager}")
public class LedvanceTurnOnExecutor extends TurnOnExecutor {
    @Autowired
    private SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway:a86htfpyzs7hnagd}")
    private String gateway;

    @Override
    public Payload executeCommand(String puid, Map<ExtraInfoKey, Object> extraInfo) {
        JsonNode jsonNode = null;
        try {
            jsonNode = toJsonNode("{\n" +
                    "      \"method\" : \"PUT\",\n" +
                    "      \"url\" : \"/v1/lights/" + getRealLedvancePuid(puid) +"\",\n" +
                    "      \"body\" : \"{\\\"onOff\\\":1}\"\n" +
                    "    }");
        } catch (IOException e) {
            log.error("I will never reach here");
        }
        duhomeClient.updateAgentDeviceProfile(gateway, jsonNode);
        return new Payload();
    }
}
