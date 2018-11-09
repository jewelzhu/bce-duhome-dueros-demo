package com.baidubce.iot.duhome.demo.custom;

import com.baidubce.iot.duhome.demo.config.ForbiddenException;
import com.baidubce.iot.duhome.demo.dueros.model.Appliance;
import com.baidubce.iot.duhome.demo.demo_use_only.RedisHelper;
import org.bouncycastle.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户应该自己实现这个bean 这里只是我的mock示例
 */
@ConditionalOnExpression("${use.mock.user.appliance.manager}")
@Component
public class MyExampleUserApplianceManager implements UserApplianceManager {
    @Autowired
    RedisHelper redisHelper;

    public void validateApplianceOwnership(String userId, String applianceId) {
        String puids = redisHelper.getString("ledvance_puids");
        if (puids == null) {
            throw new ForbiddenException();
        }
        if (!("bob".equals(userId) && puids.contains(applianceId))) {
            throw new ForbiddenException();
        }
    }

    public List<Appliance> getAppliancesByUserId(String userId) {
        List<Appliance> appliances = new ArrayList<>();
        String registeredPuids = redisHelper.getString("ledvance_puids");
        if (registeredPuids == null) {
            registeredPuids = "";
        }
        switch (userId) {
            case "bob":
                List<String> names = Arrays.asList("一号灯", "二号灯", "三号灯");
                String[] puids =  Strings.split(registeredPuids, ',');
                for (int i = 0; i < puids.length ; i++) {
                    Appliance appliance1 = new Appliance();
                    appliance1.setActions(Arrays.asList("turnOn", "turnOff",
                            "incrementBrightnessPercentage", "decrementBrightnessPercentage", "setBrightnessPercentage",
                            "incrementTemperature", "decrementTemperature",
                            "setColor"));
                    appliance1.setApplianceTypes(Arrays.asList("LIGHT"));
                    appliance1.setApplianceId(puids[i]);
                    appliance1.setFriendlyName(names.get(i));
                    appliance1.setFriendlyDescription("desc");
                    appliance1.setManufacturerName("duhometest");
                    appliance1.setModelName("testModel");
                    appliance1.setVersion("testVersion");
                    appliance1.setReachable(true);
                    appliances.add(appliance1);
                }
        }
        return appliances;
    }
}
