package com.baidubce.services.smarthome;

import com.baidubce.AbstractBceClient;
import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.SignOptions;
import com.baidubce.http.HttpMethodName;
import com.baidubce.http.handler.BceErrorResponseHandler;
import com.baidubce.http.handler.BceJsonResponseHandler;
import com.baidubce.http.handler.BceMetadataResponseHandler;
import com.baidubce.http.handler.HttpResponseHandler;
import com.baidubce.internal.InternalRequest;
import com.baidubce.model.AbstractBceResponse;
import com.baidubce.services.smarthome.model.ActiveC2cDeviceRequest;
import com.baidubce.services.smarthome.model.ActiveC2cDeviceResponse;
import com.baidubce.services.smarthome.model.ActiveDeviceRequest;
import com.baidubce.services.smarthome.model.DeactiveDeviceRequest;
import com.baidubce.services.smarthome.model.DeviceActivationStatusResponse;
import com.baidubce.services.smarthome.model.DeviceConnectStatusResponse;
import com.baidubce.services.smarthome.model.DeviceSecurityInfoResponse;
import com.baidubce.services.smarthome.model.DeviceStatusResponse;
import com.baidubce.services.smarthome.model.OtaJobRequest;
import com.baidubce.services.smarthome.model.OtaJobResponse;
import com.baidubce.services.smarthome.model.UpdateDeviceProfileRequest;
import com.baidubce.services.smarthome.util.RequestBuilder;
import com.baidubce.services.smarthome.util.SecurityUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides the client for accessing the Smart Home(DuHome)
 */
public class SmarthomeClient extends AbstractBceClient {

    protected static final String ENDPOINT_VERSION = "/v1";

    private static final HttpResponseHandler[] SMARTHOME_HANDLERS = {
            new BceMetadataResponseHandler(),
            new BceErrorResponseHandler(),
            new BceJsonResponseHandler()
    };

    public SmarthomeClient(BceClientConfiguration config) {
        super(config, SMARTHOME_HANDLERS, false);
    }

    public void createDeviceSecurityInfo(String puid) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.POST, getEndpoint())
                .path(getDevicePath(puid))
                .path("/security/create")
                .build();
        request(internalRequest);
    }

    public DeviceSecurityInfoResponse getDeviceSecurityInfo(String puid) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.GET, getEndpoint())
                .path(getDevicePath(puid))
                .path("/security")
                .build();
        return request(internalRequest, DeviceSecurityInfoResponse.class);
    }

    public void activeDevice(String puid, String privateKey) {
        activeDevice(puid, privateKey, null, null, null);
    }

    public void activeDevice(String puid, String privateKey, String getwayPuid, String getwayUuid, String se) {
        ActiveDeviceRequest activeDeviceRequest = new ActiveDeviceRequest();
        activeDeviceRequest.setGatewayPuid(getwayPuid);
        activeDeviceRequest.setGatewayUuid(getwayUuid);
        activeDeviceRequest.setSe(se);
        activeDeviceRequest.setSignature(SecurityUtils.sha256rsa(puid.getBytes(), privateKey));
        activeDevice(puid, activeDeviceRequest);
    }

    public void activeDevice(String puid, ActiveDeviceRequest activeDeviceRequest) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.POST, getEndpoint())
                .path(getDevicePath(puid))
                .path("/active")
                .addBody(activeDeviceRequest)
                .build();
        request(internalRequest);
    }

    public DeviceActivationStatusResponse activeDeviceAwait(String puid, ActiveDeviceRequest activeDeviceRequest) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.POST, getEndpoint())
                .path(getDevicePath(puid))
                .path("/activeAwait")
                .addBody(activeDeviceRequest)
                .build();
        return request(internalRequest, DeviceActivationStatusResponse.class);
    }

    public void deactiveDevice(String puid, String privateKey) {
        deactiveDevice(puid, privateKey, null, null, null);
    }

    public void deactiveDevice(String puid, String privateKey, String getwayPuid, String getwayUuid, String se) {
        DeactiveDeviceRequest deactiveDeviceRequest = new DeactiveDeviceRequest();
        deactiveDeviceRequest.setGatewayPuid(getwayPuid);
        deactiveDeviceRequest.setGatewayUuid(getwayUuid);
        deactiveDeviceRequest.setSe(se);
        deactiveDeviceRequest.setSignature(SecurityUtils.sha256rsa(puid.getBytes(), privateKey));
        deactiveDevice(puid, deactiveDeviceRequest);
    }

    public void deactiveDevice(String puid, DeactiveDeviceRequest deactiveDeviceRequest) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.POST, getEndpoint())
                .path(getDevicePath(puid))
                .path("/deactive")
                .addBody(deactiveDeviceRequest)
                .build();
        request(internalRequest);
    }

    public DeviceStatusResponse getDeviceStatus(String puid) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.GET, getEndpoint())
                .path(getDevicePath(puid))
                .path("/status")
                .build();
        return request(internalRequest, DeviceStatusResponse.class);
    }

    public DeviceActivationStatusResponse getDeviceActivationStatus(String puid) {

        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.GET, getEndpoint())
                .path(getDevicePath(puid))
                .path("/status/activation")
                .build();
        return request(internalRequest, DeviceActivationStatusResponse.class);
    }

    public DeviceConnectStatusResponse getDeviceConnectionStatus(String puid) {

        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.GET, getEndpoint())
                .path(getDevicePath(puid))
                .path("/status/connect")
                .build();
        return request(internalRequest, DeviceConnectStatusResponse.class);
    }

    public void updatDeviceProfile(String puid, JsonNode paras) {
        UpdateDeviceProfileRequest updateDeviceProfileRequest = new UpdateDeviceProfileRequest();
        updateDeviceProfileRequest.setDeviceParas(paras);
        updatDeviceProfile(puid, updateDeviceProfileRequest);
    }

    public void updatDeviceProfile(String puid, UpdateDeviceProfileRequest updateDeviceProfileRequest) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.PUT, getEndpoint())
                .path(getDevicePath(puid))
                .path("/profile")
                .addBody(updateDeviceProfileRequest)
                .build();
        request(internalRequest);
    }

    public OtaJobResponse otaJob(String uuid, List<String> puidList) {
        OtaJobRequest otaJobRequest = new OtaJobRequest();
        otaJobRequest.setPuidList(puidList);
        return otaJob(uuid, otaJobRequest);
    }

    public OtaJobResponse otaJob(String uuid, OtaJobRequest otaJobRequest) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.POST, getEndpoint())
                .path(ENDPOINT_VERSION)
                .path("/product/" + uuid)
                .path("/ota/job")
                .addBody(otaJobRequest)
                .build();
        return request(internalRequest, OtaJobResponse.class);
    }

    public ActiveC2cDeviceResponse activeC2cDevice(String uuid, String sid, List<String> customDeviceIds) {
        ActiveC2cDeviceRequest activeC2cDeviceRequest = new ActiveC2cDeviceRequest();
        activeC2cDeviceRequest.setUuid(uuid);
        activeC2cDeviceRequest.setSid(sid);
        activeC2cDeviceRequest.setCustomDeviceIds(customDeviceIds);
        return activeC2cDevice(activeC2cDeviceRequest);
    }

    public ActiveC2cDeviceResponse activeC2cDevice(ActiveC2cDeviceRequest activeC2cDeviceRequest) {
        InternalRequest internalRequest = RequestBuilder.getInstance(HttpMethodName.POST, getEndpoint())
                .path(ENDPOINT_VERSION)
                .path("/c2c/device/activate")
                .addBody(activeC2cDeviceRequest)
                .build();
        return request(internalRequest, ActiveC2cDeviceResponse.class);
    }


    private String getDevicePath(String puid) {
        return ENDPOINT_VERSION + "/device/" + puid;
    }

    protected void request(InternalRequest request) {
        request(request, AbstractBceResponse.class);
    }

    protected  <T extends AbstractBceResponse> T request(InternalRequest request, Class<T> responseClass) {
        Set<String> signHeaders = new HashSet<>();
        signHeaders.add("Host");
        signHeaders.add("Content-Type");
        signHeaders.add("Date");
        SignOptions signOptions = new SignOptions();
        signOptions.setHeadersToSign(signHeaders);
        request.setSignOptions(signOptions);
        return invokeHttpClient(request, responseClass);
    }

}
