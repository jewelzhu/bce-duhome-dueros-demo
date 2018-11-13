package com.baidubce.iot.dueros.demo.ledvance.executors;

import com.baidubce.iot.dueros.demo.ledvance.CurrentStateManager;
import com.baidubce.iot.dueros.demo.ledvance.StaticUtil;
import com.baidubce.iot.dueros.bot.executor.light.SetBrightnessExecutor;
import com.baidubce.iot.dueros.bot.model.BrightnessState;
import com.baidubce.iot.dueros.bot.model.ChangeBrightnessPercentageConfirmationPayload;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.Percentage;
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
public class LedvanceSetBrightnessExecutor implements SetBrightnessExecutor {
    @Autowired
    SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway}")
    private String gateway;

    @Autowired
    CurrentStateManager currentStateManager;

    @Override
    public ChangeBrightnessPercentageConfirmationPayload executeCommand(String puid, Map<ExtraInfoKey, Object> extraInfo) {
        int currentBrightness = currentStateManager.getCurrentBrightness(puid);
        int newBrightness = (int)((Percentage)extraInfo.get(ExtraInfoKey.BrightnessPercentage)).getValue();
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonHelper.toJsonNode(" {\n" +
                    "    \"method\": \"PUT\",\n" +
                    "    \"url\": \"/v1/lights/" + StaticUtil.getRealLedvancePuid(puid) + "\",\n" +
                    "    \"body\": \"{\\\"level\\\":" + newBrightness + "}\"\n" +
                    "  }");
        } catch (IOException e) {
            log.error("I will never reach here");
        }
        duhomeClient.updateAgentDeviceProfile(gateway, jsonNode);
        currentStateManager.saveCurrentBrightness(puid, newBrightness);
        ChangeBrightnessPercentageConfirmationPayload payload = new ChangeBrightnessPercentageConfirmationPayload();
        payload.setPreviousState(new BrightnessState(new Percentage(currentBrightness)));
        payload.setBrightness(new Percentage(newBrightness));
        return payload;
    }
}
