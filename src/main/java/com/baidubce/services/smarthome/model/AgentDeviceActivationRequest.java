package com.baidubce.services.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by shikaiyuan on 2018/10/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentDeviceActivationRequest extends SmarthomeRequestImpl {
    private String companyId;
    private String deviceId;
    private String token;
    private String macAddress;
    private String description;

    public String getCompanyId() {
        return companyId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getToken() {
        return token;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
