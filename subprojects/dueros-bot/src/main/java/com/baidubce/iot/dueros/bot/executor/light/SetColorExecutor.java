package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.CommandName;
import com.baidubce.iot.dueros.bot.model.SetColorConfirmationPayload;

public abstract class SetColorExecutor implements CommandExecutor<SetColorConfirmationPayload> {

    @Override
    public CommandName support() {
        return CommandName.SetColorRequest;
    }

}
