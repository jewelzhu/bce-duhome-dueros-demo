package com.baidubce.services.smarthome.model;

import java.util.List;

/**
 * Represent the request for activating c2c device
 */
public class ActiveC2cDeviceRequest extends SmarthomeRequestImpl {
    private String uuid;
    private String sid;
    private List<String> customDeviceIds;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public List<String> getCustomDeviceIds() {
        return customDeviceIds;
    }

    public void setCustomDeviceIds(List<String> customDeviceIds) {
        this.customDeviceIds = customDeviceIds;
    }
}
