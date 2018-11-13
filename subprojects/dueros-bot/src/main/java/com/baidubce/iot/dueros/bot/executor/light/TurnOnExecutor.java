package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.CommandName;
import com.baidubce.iot.dueros.bot.model.Payload;

public interface TurnOnExecutor extends CommandExecutor<Payload> {

    default CommandName support() {
        return CommandName.TurnOnRequest;
    }

}
