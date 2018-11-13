package com.baidubce.services.smarthome.model;

/**
 * Created by shikaiyuan on 2018/11/2.
 */
public class AgentDeviceDeactivationRequest extends SmarthomeRequestImpl {

    private String puid;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }
}
