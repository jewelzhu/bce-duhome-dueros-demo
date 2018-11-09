package com.baidubce.iot.duhome.demo.custom;

import com.baidubce.iot.duhome.demo.dueros.model.Appliance;

import java.util.List;

public interface UserApplianceManager {
    void validateApplianceOwnership(String userId, String applianceId);
    List<Appliance> getAppliancesByUserId(String userId);
}
