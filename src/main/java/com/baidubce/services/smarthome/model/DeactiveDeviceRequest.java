package com.baidubce.services.smarthome.model;

/**
 * Represent the request for deactivating device
 */
public class DeactiveDeviceRequest extends SmarthomeRequestImpl {
    private String gatewayPuid;
    private String gatewayUuid;
    private String signature;
    private String se;

    public String getGatewayPuid() {
        return gatewayPuid;
    }

    public void setGatewayPuid(String gatewayPuid) {
        this.gatewayPuid = gatewayPuid;
    }

    public String getGatewayUuid() {
        return gatewayUuid;
    }

    public void setGatewayUuid(String gatewayUuid) {
        this.gatewayUuid = gatewayUuid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }
}
