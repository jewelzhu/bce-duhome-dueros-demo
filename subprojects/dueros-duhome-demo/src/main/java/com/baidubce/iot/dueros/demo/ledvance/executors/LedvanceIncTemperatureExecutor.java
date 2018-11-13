package com.baidubce.iot.dueros.demo.ledvance.executors;

import com.baidubce.iot.dueros.demo.ledvance.CurrentStateManager;
import com.baidubce.iot.dueros.demo.ledvance.StaticUtil;
import com.baidubce.iot.dueros.bot.executor.light.IncTemperatureExecutor;
import com.baidubce.iot.dueros.bot.model.ChangeTemperatureConfirmationPayload;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.TemperatureState;
import com.baidubce.iot.dueros.bot.model.TemperatureValue;
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
public class LedvanceIncTemperatureExecutor implements IncTemperatureExecutor {
    @Autowired
    SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway}")
    private String gateway;

    @Autowired
    CurrentStateManager currentStateManager;

    @Override
    public ChangeTemperatureConfirmationPayload executeCommand(String puid, Map<ExtraInfoKey, Object> extraInfo) {
        TemperatureValue temperatureValue = (TemperatureValue) extraInfo.get(ExtraInfoKey.DeltaTemperature);
        int currentTemperature = currentStateManager.getCurrentTemperature(puid);
        if (currentTemperature == 0) {
            currentTemperature = StaticUtil.maxTemperature;
        }
        int newValue = (int)Math.min(currentTemperature + StaticUtil.temperatureChangeUnit * temperatureValue.getValue(),
                StaticUtil.maxTemperature);
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonHelper.toJsonNode(" {\n" +
                    "    \"method\": \"PUT\",\n" +
                    "    \"url\": \"/v1/lights/" + StaticUtil.getRealLedvancePuid(puid) + "\",\n" +
                    "    \"body\": \"{\\\"cct\\\":" + newValue + "}\"\n" +
                    "  }");
        } catch (IOException e) {
            log.error("I will never reach here");
        }
        duhomeClient.updateAgentDeviceProfile(gateway, jsonNode);
        currentStateManager.saveCurrentTemperature(puid, newValue);
        ChangeTemperatureConfirmationPayload payload = new ChangeTemperatureConfirmationPayload();
        payload.setPreviousState(new TemperatureState(new TemperatureValue(currentTemperature)));
        payload.setTemperature(new TemperatureValue(newValue));
        return payload;
    }
}
