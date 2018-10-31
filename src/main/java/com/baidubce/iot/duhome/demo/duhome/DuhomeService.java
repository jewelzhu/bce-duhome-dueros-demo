package com.baidubce.iot.duhome.demo.duhome;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.smarthome.SmarthomeClient;
import com.baidubce.services.smarthome.model.ActiveDeviceRequest;
import com.baidubce.services.smarthome.model.DeviceStatusResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@Slf4j
public class DuhomeService {
    @Getter
    private SmarthomeClient duhomeClient;

    @Value("${bce.ak}")
    @Setter
    private String ak;

    @Value("${bce.sk}")
    @Setter
    private String sk;

    @PostConstruct
    public void setup() {
        BceClientConfiguration bceClientConfiguration = new BceClientConfiguration()
                .withCredentials(new DefaultBceCredentials(ak, sk)).withEndpoint("smarthome.gz.baidubce.com");
        duhomeClient = new SmarthomeClient(bceClientConfiguration);
    }

    public void turnOnLight(String puid) {
        JsonNode jsonNode = null;
        try {
            jsonNode = toJsonNode("{\"power\":\"on\"}");
        } catch (IOException e) {
            log.error("I will never reach here");
        }
        duhomeClient.updatDeviceProfile(puid, jsonNode);
    }

    public void turnOffLight(String puid) {
        JsonNode jsonNode = null;
        try {
            jsonNode = toJsonNode("{\"power\":\"off\"}");
        } catch (IOException e) {
            log.error("I will never reach here");
        }
        duhomeClient.updatDeviceProfile(puid, jsonNode);
    }

    private JsonNode toJsonNode(String json) throws IOException {
        return new ObjectMapper().readTree(json);
    }
}
