package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;

/**
 * Created by shikaiyuan on 2018/11/2.
 */
public class AgentDeviceDeactivationStatus extends AbstractBceResponse {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
