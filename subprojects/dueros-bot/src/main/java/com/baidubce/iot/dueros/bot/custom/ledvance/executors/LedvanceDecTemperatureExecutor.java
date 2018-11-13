package com.baidubce.iot.dueros.bot.custom.ledvance.executors;

import com.baidubce.iot.dueros.bot.demo_use_only.CurrentStateManager;
import com.baidubce.iot.dueros.bot.demo_use_only.StaticUtil;
import com.baidubce.iot.dueros.bot.executor.light.DecTemperatureExecutor;
import com.baidubce.iot.dueros.bot.model.ChangeTemperatureConfirmationPayload;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.TemperatureState;
import com.baidubce.iot.dueros.bot.model.TemperatureValue;
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
@ConditionalOnExpression("${use.mock.user.appliance.manager:false}")
public class LedvanceDecTemperatureExecutor extends DecTemperatureExecutor {
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
            currentTemperature = StaticUtil.minTemperature;
        }
        int newValue = (int)Math.max(currentTemperature - StaticUtil.temperatureChangeUnit * temperatureValue.getValue(),
                StaticUtil.minTemperature);
        JsonNode jsonNode = null;
        try {
            jsonNode = toJsonNode(" {\n" +
                    "    \"method\": \"PUT\",\n" +
                    "    \"url\": \"/v1/lights/" + getRealLedvancePuid(puid) + "\",\n" +
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
