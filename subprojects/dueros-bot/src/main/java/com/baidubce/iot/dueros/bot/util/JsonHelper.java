package com.baidubce.iot.dueros.bot.util;

import com.baidubce.iot.dueros.bot.model.BotData;

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
            Class c;
            try {
                c = Class.forName("com.baidubce.iot.dueros.bot.model." + type);
            } catch (Exception e) {
                log.error("Invalid header name {}", type, e);
                throw new RuntimeException("Invalid header name " + type);
            }
            return (BotData) fromJsonString(jsonString, c);
        } catch (IOException e) {
            log.error("deserialize json request fail", e);
            throw new RuntimeException(e.getMessage());
        }
    }


    public static <T> T fromJsonString(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse Json String.", e);
        }
    }
}
