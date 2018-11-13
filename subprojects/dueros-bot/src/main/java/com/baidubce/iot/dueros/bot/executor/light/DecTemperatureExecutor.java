package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.CommandName;
import com.baidubce.iot.dueros.bot.model.ChangeTemperatureConfirmationPayload;

public abstract class DecTemperatureExecutor implements CommandExecutor<ChangeTemperatureConfirmationPayload> {
    @Override
    public CommandName support() {
        return CommandName.DecrementTemperatureRequest;
    }
}
