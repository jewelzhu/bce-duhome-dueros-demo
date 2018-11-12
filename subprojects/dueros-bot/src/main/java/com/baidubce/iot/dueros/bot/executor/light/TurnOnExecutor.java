package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.CommandName;
import com.baidubce.iot.dueros.bot.model.Payload;

public abstract class TurnOnExecutor implements CommandExecutor<Payload> {

    @Override
    public CommandName support() {
        return CommandName.TurnOnRequest;
    }

}
