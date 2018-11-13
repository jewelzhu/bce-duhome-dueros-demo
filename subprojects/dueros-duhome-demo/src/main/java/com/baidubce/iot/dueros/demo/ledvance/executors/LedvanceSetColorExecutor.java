package com.baidubce.iot.dueros.demo.ledvance.executors;

import com.baidubce.iot.dueros.demo.ledvance.StaticUtil;
import com.baidubce.iot.dueros.bot.executor.light.SetColorExecutor;
import com.baidubce.iot.dueros.bot.model.ColorState;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.HSBColor;
import com.baidubce.iot.dueros.bot.model.SetColorConfirmationPayload;
import com.baidubce.iot.dueros.bot.util.ColorHelper;
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
public class LedvanceSetColorExecutor implements SetColorExecutor {
    @Autowired
    SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway:a86htfpyzs7hnagd}")
    private String gateway;

    @Override
    public SetColorConfirmationPayload executeCommand(String puid, Map<ExtraInfoKey, Object> extraInfo) {
        HSBColor color = (HSBColor) extraInfo.get(ExtraInfoKey.Color);
        String rgb = ColorHelper.HSBtoRGB(color.getHue(), color.getSaturation(), color.getBrightness());
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonHelper.toJsonNode(" {\n" +
                    "    \"method\": \"PUT\",\n" +
                    "    \"url\": \"/v1/lights/" + StaticUtil.getRealLedvancePuid(puid) + "\",\n" +
                    "    \"body\": \"{\\\"rgb\\\":\\\"" + rgb + "\\\"}\"\n" +
                    "  }");
        } catch (IOException e) {
            log.error("I will never reach here");
        }
        duhomeClient.updateAgentDeviceProfile(gateway, jsonNode);
        SetColorConfirmationPayload payload = new SetColorConfirmationPayload();
        payload.setAchievedState(new ColorState(color));
        return payload;
    }
}
