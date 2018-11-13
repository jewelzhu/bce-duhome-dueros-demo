package com.baidubce.iot.dueros.demo.ledvance.executors;

import com.baidubce.iot.dueros.demo.ledvance.CurrentStateManager;
import com.baidubce.iot.dueros.demo.ledvance.StaticUtil;
import com.baidubce.iot.dueros.bot.executor.light.DecBrightnessExecutor;
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

/**
 * 这是一个示例的DecrementBrightnessPercentageRequest命令的CommandExecutor实现，实际使用时请替换成你自己实现的bean或者基于此版本进行修改
 */
@Component
@Slf4j
@ConditionalOnExpression("${demo.ledvance:false}")
public class LedvanceDecBrightnessExecutor implements DecBrightnessExecutor {
    @Autowired
    SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway}")
    private String gateway;

    @Autowired
    CurrentStateManager currentStateManager;

    @Override
    public ChangeBrightnessPercentageConfirmationPayload executeCommand(String puid,
                                                                        Map<ExtraInfoKey, Object> extraInfo) {
        int currentBrightness = currentStateManager.getCurrentBrightness(puid);
        double deltaPercentage = ((Percentage) extraInfo.get(ExtraInfoKey.DeltaBrightnessPercentage)).getValue();
        int newBrightness = (int)Math.max(currentBrightness - deltaPercentage, 1);
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
