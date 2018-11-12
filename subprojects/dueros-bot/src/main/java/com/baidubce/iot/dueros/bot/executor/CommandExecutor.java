package com.baidubce.iot.dueros.bot.executor;

import com.baidubce.iot.dueros.bot.model.CommandName;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.Payload;

import java.util.Map;

public interface CommandExecutor<T extends Payload> {
    CommandName support();
    T executeCommand(String puid, Map<ExtraInfoKey, Object> extraInfo);
}
