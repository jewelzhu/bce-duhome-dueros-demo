package com.baidubce.iot.duhome.demo.controller;

import com.baidubce.iot.duhome.demo.duhome.DuhomeService;
import com.baidubce.iot.duhome.demo.dueros.model.BotData;
import com.baidubce.iot.duhome.demo.dueros.model.CommandName;
import com.baidubce.iot.duhome.demo.dueros.model.Appliance;
import com.baidubce.iot.duhome.demo.dueros.model.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class BotController {
    @Autowired
    DuhomeService duhomeService;

    @Value("${my.test.puid}")
    String testPuid;

    @PostMapping("/api/bot")
    public BotData duerosCommand(HttpServletRequest req, @RequestBody BotData request) {
        BotData botResponse;
        // 在这里你可以获取用户的userId从而与请求中的appliance资源进行关联和鉴权
        String userId = req.getUserPrincipal().getName();
        log.info("Hello " + userId);
        switch (request.getHeader().getName()) {
            case DiscoverAppliancesRequest:
                botResponse = buildMockDiscoverApplianceResponse(request);
                break;
            case TurnOnRequest:
                String applianceId = request.getPayload().getAppliance().getApplianceId();
                log.info("turn on appliance {}", applianceId);
                validateApplianceOwnership(userId, applianceId);
                duhomeService.turnOnLight(applianceId);
                // call duhome
                botResponse = buildConfirmationResponse(request, CommandName.TurnOnConfirmation);
                break;
            case TurnOffRequest:
                // call duhome
                applianceId = request.getPayload().getAppliance().getApplianceId();
                log.info("turn off appliance {}", applianceId);
                duhomeService.turnOffLight(applianceId);
                // call duhome
                botResponse = buildConfirmationResponse(request, CommandName.TurnOffConfirmation);
                break;
            default:
                log.error("unknown command {}", request.getHeader().getName());
                return null;
        }
        return botResponse;
    }

    private void validateApplianceOwnership(String userId, String applianceId) {
        // TODO 用户应该在此处调用自己的服务，判定userId与applianceId的从属关系
    }

    private BotData buildMockDiscoverApplianceResponse(BotData request) {
        String puid = testPuid; // 设备的唯一id，例如你在duhome的灯的puid
        BotData botResponse = new BotData();
        BotData.Header header = new BotData.Header();
        BotData.Payload payload = new BotData.Payload();
        header.setMessageId(request.getHeader().getMessageId());
        header.setPayloadVersion(String.valueOf(Integer.valueOf(request.getHeader().getPayloadVersion()) + 1));
        header.setName(CommandName.DiscoverAppliancesResponse);
        header.setNamespace(request.getHeader().getNamespace());
        botResponse.setHeader(header);

        List<Appliance> applianceList = new ArrayList<>();
        Appliance appliance1 = new Appliance();
        appliance1.setActions(Arrays.asList("turnOn", "turnOff"));
        appliance1.setApplianceTypes(Arrays.asList("LIGHT"));
        appliance1.setApplianceId(puid);
        appliance1.setFriendlyName("小夜灯");
        appliance1.setFriendlyDescription("desc");
        appliance1.setManufacturerName("duhometest");
        appliance1.setModelName("testModel");
        appliance1.setVersion("testVersion");
        appliance1.setReachable(true);
        applianceList.add(appliance1);
        payload.setDiscoveredAppliances(applianceList);
        Group group = new Group();
        group.setGroupName("myGroup");
        group.setApplianceIds(Arrays.asList(puid));
        payload.setDiscoveredGroups(Arrays.asList(group));
        botResponse.setPayload(payload);

        return botResponse;
    }

    private BotData buildConfirmationResponse(BotData request, CommandName confirmCommand) {
        BotData botResponse = new BotData();
        BotData.Header header = new BotData.Header();
        header.setMessageId(request.getHeader().getMessageId());
        header.setName(confirmCommand);
        header.setNamespace(request.getHeader().getNamespace());
        botResponse.setHeader(header);
        botResponse.setPayload(new BotData.Payload());
        return botResponse;
    }
}
