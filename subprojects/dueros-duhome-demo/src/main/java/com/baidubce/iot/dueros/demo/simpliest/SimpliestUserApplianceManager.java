package com.baidubce.iot.dueros.demo.simpliest;

import com.baidubce.iot.dueros.bot.exception.ForbiddenException;
import com.baidubce.iot.dueros.bot.model.Appliance;
import com.baidubce.iot.dueros.bot.service.UserApplianceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户应该自己实现这个bean 这里只是我的mock示例
 */
@ConditionalOnExpression("${demo.simpliest:false}")
@Component
public class SimpliestUserApplianceManager implements UserApplianceManager {
    @Value("${my.test.puid}")
    String testPuid;

    public void validateApplianceOwnership(String userId, String applianceId) throws ForbiddenException {
        if (!("bob".equals(userId) && testPuid.equals(applianceId))) {
            throw new ForbiddenException();
        }
    }

    public List<Appliance> getAppliancesByUserId(String userId) {
        List<Appliance> appliances = new ArrayList<>();
        switch (userId) {
            case "bob":
                Appliance appliance1 = new Appliance();
                appliance1.setActions(Arrays.asList("turnOn", "turnOff"));
                appliance1.setApplianceTypes(Arrays.asList("LIGHT"));
                appliance1.setApplianceId(testPuid);
                appliance1.setFriendlyName("小夜灯");
                appliance1.setFriendlyDescription("desc");
                appliance1.setManufacturerName("duhometest");
                appliance1.setModelName("testModel");
                appliance1.setVersion("testVersion");
                appliance1.setReachable(true);
                appliances.add(appliance1);
        }
        return appliances;
    }
}
