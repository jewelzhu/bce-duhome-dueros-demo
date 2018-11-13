package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.ChangeTemperatureConfirmationPayload;
import com.baidubce.iot.dueros.bot.model.CommandName;

public interface IncTemperatureExecutor extends CommandExecutor<ChangeTemperatureConfirmationPayload> {
    default CommandName support() {
        return CommandName.IncrementTemperatureRequest;
    }
}
