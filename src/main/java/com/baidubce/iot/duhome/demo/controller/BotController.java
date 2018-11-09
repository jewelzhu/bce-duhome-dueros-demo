package com.baidubce.iot.duhome.demo.controller;

import com.baidubce.iot.duhome.demo.custom.UserApplianceManager;
import com.baidubce.iot.duhome.demo.dueros.model.DecrementBrightnessPercentageConfirmation;
import com.baidubce.iot.duhome.demo.dueros.model.DecrementBrightnessPercentageRequest;
import com.baidubce.iot.duhome.demo.dueros.model.DecrementTemperatureConfirmation;
import com.baidubce.iot.duhome.demo.dueros.model.DecrementTemperatureRequest;
import com.baidubce.iot.duhome.demo.dueros.model.DiscoverAppliancesResponse;
import com.baidubce.iot.duhome.demo.dueros.model.DiscoverAppliancesResponsePayload;
import com.baidubce.iot.duhome.demo.dueros.model.ExtraInfoKey;
import com.baidubce.iot.duhome.demo.dueros.model.Header;
import com.baidubce.iot.duhome.demo.dueros.model.IncrementBrightnessPercentageConfirmation;
import com.baidubce.iot.duhome.demo.dueros.model.IncrementBrightnessPercentageRequest;
import com.baidubce.iot.duhome.demo.dueros.model.IncrementTemperatureConfirmation;
import com.baidubce.iot.duhome.demo.dueros.model.IncrementTemperatureRequest;
import com.baidubce.iot.duhome.demo.dueros.model.Payload;
import com.baidubce.iot.duhome.demo.dueros.model.PayloadWithSingleAppliance;
import com.baidubce.iot.duhome.demo.dueros.model.SetBrightnessPercentageConfirmation;
import com.baidubce.iot.duhome.demo.dueros.model.SetBrightnessPercentageRequest;
import com.baidubce.iot.duhome.demo.dueros.model.SetColorConfirmation;
import com.baidubce.iot.duhome.demo.dueros.model.SetColorRequest;
import com.baidubce.iot.duhome.demo.dueros.model.TurnOffConfirmation;
import com.baidubce.iot.duhome.demo.dueros.model.TurnOnConfirmation;
import com.baidubce.iot.duhome.demo.dueros.service.CommandExecutionService;
import com.baidubce.iot.duhome.demo.dueros.model.BotData;
import com.baidubce.iot.duhome.demo.dueros.model.CommandName;
import com.baidubce.iot.duhome.demo.dueros.model.Appliance;
import com.baidubce.iot.duhome.demo.dueros.model.Group;
import com.baidubce.iot.duhome.demo.util.JsonHelper;
import com.baidubce.iot.duhome.demo.demo_use_only.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    RedisHelper redisHelper;
    @Autowired
    UserApplianceManager userApplianceManager;

    @Value("${my.test.puid}")
    String testPuid;

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
        switch (confirmCommand) {
            case TurnOffConfirmation:
                botResponse = new TurnOffConfirmation();
                break;
            case TurnOnConfirmation:
                botResponse = new TurnOnConfirmation();
                break;
            case IncrementBrightnessPercentageConfirmation:
                botResponse = new IncrementBrightnessPercentageConfirmation();
                break;
            case DiscoverAppliancesResponse:
                botResponse = new DiscoverAppliancesResponse();
                break;
            case DecrementBrightnessPercentageConfirmation:
                botResponse = new DecrementBrightnessPercentageConfirmation();
                break;
            case SetBrightnessPercentageConfirmation:
                botResponse = new SetBrightnessPercentageConfirmation();
                break;
            case SetColorConfirmation:
                botResponse = new SetColorConfirmation();
                break;
            case IncrementTemperatureConfirmation:
                botResponse = new IncrementTemperatureConfirmation();
                break;
            case DecrementTemperatureConfirmation:
                botResponse = new DecrementTemperatureConfirmation();
                break;
            default:
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
