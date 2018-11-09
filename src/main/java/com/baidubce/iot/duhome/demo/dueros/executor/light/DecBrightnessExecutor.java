package com.baidubce.iot.duhome.demo.dueros.executor.light;

import com.baidubce.iot.duhome.demo.dueros.model.BrightnessState;
import com.baidubce.iot.duhome.demo.dueros.model.ChangeBrightnessPercentageConfirmationPayload;
import com.baidubce.iot.duhome.demo.dueros.model.CommandName;
import com.baidubce.iot.duhome.demo.dueros.model.ExtraInfoKey;
import com.baidubce.iot.duhome.demo.dueros.model.Percentage;
import com.baidubce.iot.duhome.demo.dueros.executor.CommandExecutor;
import com.baidubce.iot.duhome.demo.demo_use_only.CurrentStateManager;
import com.baidubce.services.smarthome.SmarthomeAgentClient;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static com.baidubce.iot.duhome.demo.demo_use_only.StaticUtil.getRealLedvancePuid;
import static com.baidubce.iot.duhome.demo.util.JsonHelper.toJsonNode;

/**
 * 这是一个示例的DecrementBrightnessPercentageRequest命令的CommandExecutor实现，实际使用时请替换成你自己实现的bean或者基于此版本进行修改
 */
@Component
@Slf4j
@ConditionalOnExpression("${use.mock.user.appliance.manager:false}")
public class DecBrightnessExecutor implements CommandExecutor<ChangeBrightnessPercentageConfirmationPayload> {
    @Autowired
    SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway}")
    private String gateway;

    @Autowired
    CurrentStateManager currentStateManager;

    @Override
    public CommandName support() {
        return CommandName.DecrementBrightnessPercentageRequest;
    }

    @Override
    public ChangeBrightnessPercentageConfirmationPayload executeCommand(String puid,
                                                                        Map<ExtraInfoKey, Object> extraInfo) {
        int currentBrightness = currentStateManager.getCurrentBrightness(puid);
        double deltaPercentage = ((Percentage) extraInfo.get(ExtraInfoKey.DeltaBrightnessPercentage)).getValue();
        int newBrightness = (int)Math.max(currentBrightness - deltaPercentage, 1);
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
