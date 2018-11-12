package com.baidubce.iot.dueros.bot.custom.ledvance.executors;

import com.baidubce.iot.dueros.bot.executor.light.SetColorExecutor;
import com.baidubce.iot.dueros.bot.model.ColorState;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.HSBColor;
import com.baidubce.iot.dueros.bot.model.SetColorConfirmationPayload;
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
import static com.baidubce.iot.dueros.bot.util.ColorHelper.HSBtoRGB;
import static com.baidubce.iot.dueros.bot.util.JsonHelper.toJsonNode;

@Component
@Slf4j
@ConditionalOnExpression("${use.mock.user.appliance.manager:false}")
public class LedvanceSetColorExecutor extends SetColorExecutor {
    @Autowired
    SmarthomeAgentClient duhomeClient;

    @Value("${my.test.gateway:a86htfpyzs7hnagd}")
    private String gateway;

    @Override
    public SetColorConfirmationPayload executeCommand(String puid, Map<ExtraInfoKey, Object> extraInfo) {
        HSBColor color = (HSBColor) extraInfo.get(ExtraInfoKey.Color);
        String rgb = HSBtoRGB(color.getHue(), color.getSaturation(), color.getBrightness());
        JsonNode jsonNode = null;
        try {
            jsonNode = toJsonNode(" {\n" +
                    "    \"method\": \"PUT\",\n" +
                    "    \"url\": \"/v1/lights/" + getRealLedvancePuid(puid) + "\",\n" +
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
