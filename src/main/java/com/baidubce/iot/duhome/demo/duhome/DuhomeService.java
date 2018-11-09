package com.baidubce.iot.duhome.demo.duhome;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.iot.duhome.demo.dueros.model.CommandName;
import com.baidubce.iot.duhome.demo.dueros.model.ExtraInfoKey;
import com.baidubce.iot.duhome.demo.dueros.model.Payload;
import com.baidubce.iot.duhome.demo.duhome.executor.CommandExecutor;
import com.baidubce.services.smarthome.SmarthomeAgentClient;
import com.baidubce.services.smarthome.SmarthomeClient;
import com.baidubce.services.smarthome.model.ActiveDeviceRequest;
import com.baidubce.services.smarthome.model.DeviceStatusResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DuhomeService {
    @Value("${bce.ak}")
    @Setter
    private String ak;

    @Value("${bce.sk}")
    @Setter
    private String sk;

    @Autowired
    List<CommandExecutor> commandExecutorList;

    private Map<CommandName, CommandExecutor> commandExecutorMap;

    @PostConstruct
    public void setup() {
        commandExecutorMap = new HashMap<>();
        for (CommandExecutor commandExecutor : commandExecutorList) {
            commandExecutorMap.put(commandExecutor.support(), commandExecutor);
        }
    }

    @Bean
    public SmarthomeClient duhomeClient() {
        BceClientConfiguration bceClientConfiguration = new BceClientConfiguration()
                .withCredentials(new DefaultBceCredentials(ak, sk)).withEndpoint("smarthome.gz.baidubce.com");
        return new SmarthomeClient(bceClientConfiguration);
    }

    @Bean
    public SmarthomeAgentClient duhomeAgentClient() {
        BceClientConfiguration bceClientConfiguration = new BceClientConfiguration()
                .withCredentials(new DefaultBceCredentials(ak, sk)).withEndpoint("smarthome.gz.baidubce.com");
        return new SmarthomeAgentClient(bceClientConfiguration);
    }

    public Payload executeCommand(String puid, CommandName command, Map<ExtraInfoKey, Object> extraInfo) {
        return commandExecutorMap.get(command).executeCommand(puid, extraInfo);
    }
}
