package com.baidubce.iot.dueros.bot.controller;

import com.baidubce.iot.dueros.bot.model.Appliance;
import com.baidubce.iot.dueros.bot.model.BotData;
import com.baidubce.iot.dueros.bot.model.CommandName;
import com.baidubce.iot.dueros.bot.model.DecrementBrightnessPercentageRequest;
import com.baidubce.iot.dueros.bot.model.DecrementTemperatureRequest;
import com.baidubce.iot.dueros.bot.model.DiscoverAppliancesResponse;
import com.baidubce.iot.dueros.bot.model.DiscoverAppliancesResponsePayload;
import com.baidubce.iot.dueros.bot.model.ExtraInfoKey;
import com.baidubce.iot.dueros.bot.model.Group;
import com.baidubce.iot.dueros.bot.model.Header;
import com.baidubce.iot.dueros.bot.model.IncrementBrightnessPercentageRequest;
import com.baidubce.iot.dueros.bot.model.IncrementTemperatureRequest;
import com.baidubce.iot.dueros.bot.model.Payload;
import com.baidubce.iot.dueros.bot.model.PayloadWithSingleAppliance;
import com.baidubce.iot.dueros.bot.model.SetBrightnessPercentageRequest;
import com.baidubce.iot.dueros.bot.model.SetColorRequest;
import com.baidubce.iot.dueros.bot.service.CommandExecutionService;
import com.baidubce.iot.dueros.bot.service.UserApplianceManager;
import com.baidubce.iot.dueros.bot.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class BotController {
    @Autowired
    CommandExecutionService commandExecutionService;
    @Autowired
    UserApplianceManager userApplianceManager;

    @PostMapping("/api/bot")
    public BotData duerosCommand(HttpServletRequest req, @RequestBody String jsonRequest) {
        BotData request = JsonHelper.deserializeJson(jsonRequest);
        BotData botResponse;
        // 在这里你可以获取用户的userId从而与请求中的appliance资源进行关联和鉴权
        String userId = req.getUserPrincipal().getName();
        log.debug("user=" + userId);
        CommandName commandName = request.getHeader().getName();
        if (CommandName.DiscoverAppliancesRequest.equals(commandName)) {
            List<Appliance> applianceList = userApplianceManager.getAppliancesByUserId(userId);
            return buildDiscoverApplianceResponse(request, applianceList);
        }
        else {
            String applianceId = ((PayloadWithSingleAppliance) request.getPayload()).getAppliance().getApplianceId();
            log.info("appliance {} cmd {}", applianceId, request.getHeader().getName());
            userApplianceManager.validateApplianceOwnership(userId, applianceId);
            Map<ExtraInfoKey, Object> extraInfos = new HashMap<>();
            // set extra info for diffrent commands
            switch (commandName) {
                case IncrementBrightnessPercentageRequest:
                    extraInfos.put(ExtraInfoKey.DeltaBrightnessPercentage,
                            ((IncrementBrightnessPercentageRequest)request).getPayload().getDeltaPercentage());
                    break;
                case DecrementBrightnessPercentageRequest:
                    extraInfos.put(ExtraInfoKey.DeltaBrightnessPercentage,
                            ((DecrementBrightnessPercentageRequest)request).getPayload().getDeltaPercentage());
                    break;
                case SetBrightnessPercentageRequest:
                    extraInfos.put(ExtraInfoKey.BrightnessPercentage,
                            ((SetBrightnessPercentageRequest)request).getPayload().getBrightness());
                    break;
                case SetColorRequest:
                    extraInfos.put(ExtraInfoKey.Color,
                            ((SetColorRequest)request).getPayload().getColor());
                    break;
                case IncrementTemperatureRequest:
                    extraInfos.put(ExtraInfoKey.DeltaTemperature,
                            ((IncrementTemperatureRequest)request).getPayload().getDeltaValue());
                    break;
                case DecrementTemperatureRequest:
                    extraInfos.put(ExtraInfoKey.DeltaTemperature,
                            ((DecrementTemperatureRequest)request).getPayload().getDeltaValue());
                    break;
                default:
                    log.info("no extra info needed for command {}", commandName);
            }
            Payload responsePayload = commandExecutionService.executeCommand(applianceId, commandName, extraInfos);
            botResponse = buildConfirmationResponse(request,
                    CommandName.getConfirmationOf(commandName), responsePayload);
        }
        return botResponse;
    }

    private DiscoverAppliancesResponse buildDiscoverApplianceResponse(BotData request, List<Appliance> applianceList) {
        DiscoverAppliancesResponse botResponse = new DiscoverAppliancesResponse();
        Header header = new Header();
        DiscoverAppliancesResponsePayload payload = new DiscoverAppliancesResponsePayload();
        header.setMessageId(request.getHeader().getMessageId());
        header.setPayloadVersion(String.valueOf(Integer.valueOf(request.getHeader().getPayloadVersion()) + 1));
        header.setName(CommandName.DiscoverAppliancesResponse);
        header.setNamespace(request.getHeader().getNamespace());
        botResponse.setHeader(header);

        Group group = new Group();
        group.setGroupName("myGroup");
        group.setApplianceIds(applianceList.stream()
                .map(Appliance::getApplianceId)
                .collect(Collectors.toList()));
        payload.setDiscoveredGroups(Arrays.asList(group));
        payload.setDiscoveredAppliances(applianceList);
        botResponse.setPayload(payload);

        return botResponse;
    }

    private BotData buildConfirmationResponse(BotData request, CommandName confirmCommand, Payload responsePayload) {
        BotData botResponse;
        try {
            Class c = Class.forName("com.baidubce.bot.model." + confirmCommand.name());
            botResponse = (BotData) c.newInstance();
        } catch (Exception e) {
            log.error("Cannot find or initiate class of name {}", confirmCommand, e);
            throw new RuntimeException("unknown confirmation type " + confirmCommand);
        }

        Header header = new Header();
        header.setMessageId(request.getHeader().getMessageId());
        header.setName(confirmCommand);
        header.setNamespace(request.getHeader().getNamespace());
        botResponse.setHeader(header);
        botResponse.setPayload(responsePayload);
        return botResponse;
    }
}
