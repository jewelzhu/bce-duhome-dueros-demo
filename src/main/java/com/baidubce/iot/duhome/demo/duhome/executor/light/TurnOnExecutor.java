package com.baidubce.iot.duhome.demo.duhome.executor.light;

import com.baidubce.iot.duhome.demo.dueros.model.BotData;
import com.baidubce.iot.duhome.demo.dueros.model.CommandName;
import com.baidubce.iot.duhome.demo.dueros.model.ExtraInfoKey;
import com.baidubce.iot.duhome.demo.dueros.model.Payload;
import com.baidubce.iot.duhome.demo.duhome.executor.CommandExecutor;
import com.baidubce.services.smarthome.SmarthomeAgentClient;
import com.baidubce.services.smarthome.SmarthomeClient;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static com.baidubce.iot.duhome.demo.util.JsonHelper.getRealLedvancePuid;
import static com.baidubce.iot.duhome.demo.util.JsonHelper.toJsonNode;

@Component
@Slf4j
public class TurnOnExecutor implements CommandExecutor<Payload> {
    @Autowired
    private SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway:a86htfpyzs7hnagd}")
    private String gateway;

    @Override
    public CommandName support() {
        return CommandName.TurnOnRequest;
    }

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
