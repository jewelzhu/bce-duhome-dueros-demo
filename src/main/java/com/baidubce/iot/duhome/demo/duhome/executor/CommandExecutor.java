package com.baidubce.iot.duhome.demo.duhome.executor;

import com.baidubce.iot.duhome.demo.dueros.model.CommandName;
import com.baidubce.iot.duhome.demo.dueros.model.ExtraInfoKey;
import com.baidubce.iot.duhome.demo.dueros.model.Payload;

import java.util.Map;

public interface CommandExecutor<T extends Payload> {
    CommandName support();
    T executeCommand(String puid, Map<ExtraInfoKey, Object> extraInfo);
}
