package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.ChangeBrightnessPercentageConfirmationPayload;
import com.baidubce.iot.dueros.bot.model.CommandName;

public abstract class SetBrightnessExecutor implements CommandExecutor<ChangeBrightnessPercentageConfirmationPayload>{

    @Override
    public CommandName support() {
        return CommandName.SetBrightnessPercentageRequest;
    }

}
