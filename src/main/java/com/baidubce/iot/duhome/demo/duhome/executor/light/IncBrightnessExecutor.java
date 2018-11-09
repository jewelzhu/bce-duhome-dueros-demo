package com.baidubce.iot.duhome.demo.duhome.executor.light;

import com.baidubce.iot.duhome.demo.dueros.model.BrightnessState;
import com.baidubce.iot.duhome.demo.dueros.model.CommandName;
import com.baidubce.iot.duhome.demo.dueros.model.ExtraInfoKey;
import com.baidubce.iot.duhome.demo.dueros.model.ChangeBrightnessPercentageConfirmationPayload;
import com.baidubce.iot.duhome.demo.dueros.model.Percentage;
import com.baidubce.iot.duhome.demo.duhome.executor.CommandExecutor;
import com.baidubce.iot.duhome.demo.duhome.executor.CurrentStateManager;
import com.baidubce.services.smarthome.SmarthomeAgentClient;
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
public class IncBrightnessExecutor implements CommandExecutor<ChangeBrightnessPercentageConfirmationPayload> {
    @Autowired
    SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway:a86htfpyzs7hnagd}")
    private String gateway;

    @Autowired
    CurrentStateManager currentStateManager;

    @Override
    public CommandName support() {
        return CommandName.IncrementBrightnessPercentageRequest;
    }

    @Override
    public ChangeBrightnessPercentageConfirmationPayload executeCommand(String puid,
                                                                        Map<ExtraInfoKey, Object> extraInfo) {
        int currentBrightness = currentStateManager.getCurrentBrightness(puid);
        if (currentBrightness == 0) {
            currentBrightness = 100;
        }
        double deltaPercentage = ((Percentage) extraInfo.get(ExtraInfoKey.DeltaBrightnessPercentage)).getValue();
        int newBrightness = (int)Math.min(currentBrightness + deltaPercentage, 100);
        JsonNode jsonNode = null;
        try {
            jsonNode = toJsonNode(" {\n" +
                    "    \"method\": \"PUT\",\n" +
                    "    \"url\": \"/v1/lights/" + getRealLedvancePuid(puid) + "\",\n" +
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
