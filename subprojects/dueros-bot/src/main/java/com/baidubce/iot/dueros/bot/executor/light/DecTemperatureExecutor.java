package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.CommandName;
import com.baidubce.iot.dueros.bot.model.ChangeTemperatureConfirmationPayload;

public interface DecTemperatureExecutor extends CommandExecutor<ChangeTemperatureConfirmationPayload> {
    default CommandName support() {
        return CommandName.DecrementTemperatureRequest;
    }
}
