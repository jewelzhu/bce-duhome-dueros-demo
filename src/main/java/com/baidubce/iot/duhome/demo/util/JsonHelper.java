package com.baidubce.iot.duhome.demo.util;

import com.baidubce.iot.duhome.demo.dueros.model.BotData;
import com.baidubce.iot.duhome.demo.dueros.model.DecrementBrightnessPercentageRequest;
import com.baidubce.iot.duhome.demo.dueros.model.DiscoverAppliancesRequest;
import com.baidubce.iot.duhome.demo.dueros.model.IncrementBrightnessPercentageRequest;
import com.baidubce.iot.duhome.demo.dueros.model.SetColorRequest;
import com.baidubce.iot.duhome.demo.dueros.model.TurnOffRequest;
import com.baidubce.iot.duhome.demo.dueros.model.TurnOnRequest;
import com.baidubce.util.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonHelper {
    public static  JsonNode toJsonNode(String json) throws IOException {
        return new ObjectMapper().readTree(json);
    }

    public static BotData deserializeJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(jsonString);

            String type = node.get("header").get("name").textValue();
            Class<? extends BotData> c;
            switch (type) {
                case "TurnOnRequest":
                    c = TurnOnRequest.class;
                    break;
                case "TurnOffRequest":
                    c = TurnOffRequest.class;
                    break;
                case "DiscoverAppliancesRequest":
                    c = DiscoverAppliancesRequest.class;
                    break;
                case "IncrementBrightnessPercentageRequest":
                    c = IncrementBrightnessPercentageRequest.class;
                    break;
                case "DecrementBrightnessPercentageRequest":
                    c = DecrementBrightnessPercentageRequest.class;
                    break;
                case "SetColorRequest":
                    c = SetColorRequest.class;
                    break;
                    default:
                        throw new RuntimeException("Invalid header.name " + type);
            }
            return JsonUtils.fromJsonString(jsonString, c);
        } catch (IOException e) {
            log.error("deserialize json request fail", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getAccessTokenInBotData(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(jsonString);
            return node.get("payload").get("accessToken").textValue();
        } catch (IOException e) {
            log.error("parse json request fail", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getRealLedvancePuid(String zPuid) {
        return zPuid.substring(2);
    }
}
