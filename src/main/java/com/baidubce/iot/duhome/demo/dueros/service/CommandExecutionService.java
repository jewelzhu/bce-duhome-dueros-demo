package com.baidubce.iot.duhome.demo.dueros.service;

import com.baidubce.iot.duhome.demo.dueros.model.CommandName;
import com.baidubce.iot.duhome.demo.dueros.model.ExtraInfoKey;
import com.baidubce.iot.duhome.demo.dueros.model.Payload;
import com.baidubce.iot.duhome.demo.dueros.executor.CommandExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CommandExecutionService {
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

    public Payload executeCommand(String puid, CommandName command, Map<ExtraInfoKey, Object> extraInfo) {
        return commandExecutorMap.get(command).executeCommand(puid, extraInfo);
    }
}
