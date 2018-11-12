package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.ChangeBrightnessPercentageConfirmationPayload;
import com.baidubce.iot.dueros.bot.model.CommandName;

public abstract class IncBrightnessExecutor implements CommandExecutor<ChangeBrightnessPercentageConfirmationPayload> {

    @Override
    public CommandName support() {
        return CommandName.IncrementBrightnessPercentageRequest;
    }

}
