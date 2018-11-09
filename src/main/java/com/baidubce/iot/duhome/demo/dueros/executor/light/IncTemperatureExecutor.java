package com.baidubce.iot.duhome.demo.dueros.executor.light;

import com.baidubce.iot.duhome.demo.demo_use_only.CurrentStateManager;
import com.baidubce.iot.duhome.demo.demo_use_only.StaticUtil;
import com.baidubce.iot.duhome.demo.dueros.executor.CommandExecutor;
import com.baidubce.iot.duhome.demo.dueros.model.ChangeTemperatureConfirmationPayload;
import com.baidubce.iot.duhome.demo.dueros.model.CommandName;
import com.baidubce.iot.duhome.demo.dueros.model.ExtraInfoKey;
import com.baidubce.iot.duhome.demo.dueros.model.TemperatureState;
import com.baidubce.iot.duhome.demo.dueros.model.TemperatureValue;
import com.baidubce.services.smarthome.SmarthomeAgentClient;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import static com.baidubce.iot.duhome.demo.demo_use_only.StaticUtil.getRealLedvancePuid;
import static com.baidubce.iot.duhome.demo.util.JsonHelper.toJsonNode;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@ConditionalOnExpression("${use.mock.user.appliance.manager:false}")
public class IncTemperatureExecutor implements CommandExecutor<ChangeTemperatureConfirmationPayload>{
    @Autowired
    SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway}")
    private String gateway;

    @Autowired
    CurrentStateManager currentStateManager;

    @Override
    public CommandName support() {
        return CommandName.IncrementTemperatureRequest;
    }

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
