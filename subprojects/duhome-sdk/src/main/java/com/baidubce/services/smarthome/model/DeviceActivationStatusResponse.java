package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;

/**
 * Represent the response for getting device's activation status
 */
public class DeviceActivationStatusResponse extends AbstractBceResponse {
    private ActivationStatus status;

    public ActivationStatus getStatus() {
        return status;
    }

    public void setStatus(ActivationStatus status) {
        this.status = status;
    }
}
