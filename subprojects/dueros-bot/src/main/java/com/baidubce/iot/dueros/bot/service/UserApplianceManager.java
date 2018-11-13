package com.baidubce.iot.dueros.bot.service;

import com.baidubce.iot.dueros.bot.config.ForbiddenException;
import com.baidubce.iot.dueros.bot.model.Appliance;

import java.util.List;

/**
 * user must provide a bean implementing this to enable BotController
 */
public interface UserApplianceManager {
    void validateApplianceOwnership(String userId, String applianceId) throws ForbiddenException;
    List<Appliance> getAppliancesByUserId(String userId);
}
