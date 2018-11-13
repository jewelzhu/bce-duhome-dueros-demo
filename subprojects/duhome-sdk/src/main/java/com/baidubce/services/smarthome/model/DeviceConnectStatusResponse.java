package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;

/**
 * Represent the response for getting device's connect status
 */
public class DeviceConnectStatusResponse extends AbstractBceResponse {
    private ConnectStatus status;

    public ConnectStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectStatus status) {
        this.status = status;
    }
}
