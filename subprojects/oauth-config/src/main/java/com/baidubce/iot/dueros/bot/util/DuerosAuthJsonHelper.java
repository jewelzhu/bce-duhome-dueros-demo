package com.baidubce.iot.dueros.bot.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class DuerosAuthJsonHelper {
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
}
