package com.baidubce.iot.dueros.bot.executor.light;

import com.baidubce.iot.dueros.bot.executor.CommandExecutor;
import com.baidubce.iot.dueros.bot.model.CommandName;
import com.baidubce.iot.dueros.bot.model.SetColorConfirmationPayload;

public interface SetColorExecutor extends CommandExecutor<SetColorConfirmationPayload> {

    default CommandName support() {
        return CommandName.SetColorRequest;
    }

}
