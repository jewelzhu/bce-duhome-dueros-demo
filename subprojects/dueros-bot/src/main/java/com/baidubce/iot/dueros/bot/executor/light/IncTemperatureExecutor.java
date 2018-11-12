package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.ChangeTemperatureConfirmationPayload;
import com.baidubce.iot.dueros.bot.model.CommandName;

public abstract class IncTemperatureExecutor implements CommandExecutor<ChangeTemperatureConfirmationPayload> {
    @Override
    public CommandName support() {
        return CommandName.IncrementTemperatureRequest;
    }
}
