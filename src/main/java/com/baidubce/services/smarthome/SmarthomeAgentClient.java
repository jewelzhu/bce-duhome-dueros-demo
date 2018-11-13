package com.baidubce.services.smarthome;

import com.baidubce.BceClientConfiguration;
import com.baidubce.http.HttpMethodName;
import com.baidubce.internal.InternalRequest;
import com.baidubce.services.smarthome.model.AgentActiveResult;
import com.baidubce.services.smarthome.model.AgentActiveResultRequest;
import com.baidubce.services.smarthome.model.AgentActiveResultResponse;
import com.baidubce.services.smarthome.model.AgentDeactivateResult;
import com.baidubce.services.smarthome.model.AgentDeactivateResultRequest;
import com.baidubce.services.smarthome.model.AgentDeviceActivationRequest;
import com.baidubce.services.smarthome.model.AgentDeviceActivationStatus;
import com.baidubce.services.smarthome.model.AgentDeviceDeactivationRequest;
import com.baidubce.services.smarthome.model.AgentDeviceDeactivationStatus;
import com.baidubce.services.smarthome.model.AgentDeviceMetadata;
import com.baidubce.services.smarthome.model.AgentDeviceStatus;
import com.baidubce.services.smarthome.model.AgentDeviceProfileUpdateRequest;
import com.baidubce.services.smarthome.model.IntStatus;
import com.baidubce.services.smarthome.util.RequestBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by shikaiyuan on 2018/10/24.
 */
@Slf4j
public class SmarthomeAgentClient extends SmarthomeClient {

    public SmarthomeAgentClient(BceClientConfiguration config) {
        super(config);
    }

    public void activeAgentDevice(String companyId, String deviceId,
                                  String token, String macAddress, String description) {
        AgentDeviceActivationRequest request = new AgentDeviceActivationRequest();
        request.setCompanyId(companyId);
        request.setDeviceId(deviceId);
        request.setToken(token);
        request.setMacAddress(macAddress);
        request.setDescription(description);
        activeAgentDevice(request);
    }

    public void activeAgentDevice(AgentDeviceActivationRequest request) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.POST, getEndpoint())
                .path(getAgentPath())
                .path("/active")
                .addBody(request)
                .build();
        request(internalRequest);
    }

    public AgentDeviceActivationStatus getAgentDeviceActivationStatus(String companyId, String deviceId) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.GET, getEndpoint())
                .path(getAgentPath())
                .path("/status/active")
                .addParam("companyId", companyId)
                .addParam("deviceId", deviceId)
                .build();
        return request(internalRequest, AgentDeviceActivationStatus.class);
    }

    public IntStatus getAgentDeviceConnectStatus(String puid) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.GET, getEndpoint())
                .path(getAgentPath())
                .path("/" + puid)
                .path("/status/connect")
                .build();
        return request(internalRequest, IntStatus.class);
    }

    public AgentDeviceStatus getAgentDeviceStatus(String puid) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.GET, getEndpoint())
                .path(getAgentPath())
                .path("/" + puid)
                .path("/status")
                .build();
        return request(internalRequest, AgentDeviceStatus.class);
    }

    public AgentDeviceMetadata getAgentDeviceMetadata(String puid) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.GET, getEndpoint())
                .path(getAgentPath())
                .path("/" + puid)
                .path("/metadata")
                .build();
        return request(internalRequest, AgentDeviceMetadata.class);
    }

    public void updateAgentDeviceProfile(String puid, JsonNode deviceParas) {
        log.info("update device profile {}", deviceParas);
        updateAgentDeviceProfile(puid, new AgentDeviceProfileUpdateRequest(deviceParas));
    }

    public void updateAgentDeviceProfile(String puid, AgentDeviceProfileUpdateRequest request) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.PUT, getEndpoint())
                .path(getAgentPath())
                .path("/" + puid)
                .path("/profile")
                .addBody(request)
                .build();
        request(internalRequest);
    }

    public AgentActiveResultResponse updateAgentActiveResult(
            String uuid, String sid,String deviceId, String companyId, AgentActiveResult result) {
        AgentActiveResultRequest request = new AgentActiveResultRequest();
        request.setUuid(uuid);
        request.setSid(sid);
        request.setDeviceId(deviceId);
        request.setCompanyId(companyId);
        request.setResult(result);
        return updateAgentActiveResult(request);
    }

    public AgentActiveResultResponse updateAgentActiveResult(AgentActiveResultRequest request) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.PUT, getEndpoint())
                .path(getAgentPath())
                .path("/active/result")
                .addBody(request)
                .build();
        return request(internalRequest, AgentActiveResultResponse.class);
    }

    public void deactivateAgentDevice(String puid) {
        AgentDeviceDeactivationRequest request = new AgentDeviceDeactivationRequest();
        request.setPuid(puid);
        deactivateAgentDevice(request);
    }

    public void deactivateAgentDevice(AgentDeviceDeactivationRequest request) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.POST, getEndpoint())
                .path(getAgentPath())
                .path("/deactivate")
                .addBody(request)
                .build();
        request(internalRequest);
    }

    public AgentDeviceDeactivationStatus getAgentDeviceDeactivationnStatus(String puid) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.GET, getEndpoint())
                .path(getAgentPath())
                .path("/status/deactivate")
                .addParam("puid", puid)
                .build();
        return request(internalRequest, AgentDeviceDeactivationStatus.class);
    }

    public void updateAgentDeactivateResult(String puid, String customDeviceId,
                                            AgentDeactivateResult result,
                                            String errMsg) {
        AgentDeactivateResultRequest request = new AgentDeactivateResultRequest();
        request.setPuid(puid);
        request.setCustomDeviceId(customDeviceId);
        request.setResult(result);
        request.setErrMsg(errMsg);
        updateAgentDeactivateResult(request);
    }

    public void updateAgentDeactivateResult(AgentDeactivateResultRequest request) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.PUT, getEndpoint())
                .path(getAgentPath())
                .path("/deactivate/result")
                .addBody(request)
                .build();
        request(internalRequest);
    }

    private String getAgentPath() {
        return ENDPOINT_VERSION + "/agent/device";
    }
}
