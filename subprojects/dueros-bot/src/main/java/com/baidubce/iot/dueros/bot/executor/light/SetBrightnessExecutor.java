package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.ChangeBrightnessPercentageConfirmationPayload;
import com.baidubce.iot.dueros.bot.model.CommandName;

public interface SetBrightnessExecutor extends CommandExecutor<ChangeBrightnessPercentageConfirmationPayload>{

    default CommandName support() {
        return CommandName.SetBrightnessPercentageRequest;
    }

}
