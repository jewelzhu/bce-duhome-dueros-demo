package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * Represent the response for getting device's detail status
 */
public class DeviceStatusResponse extends AbstractBceResponse {

    private String uuid;
    private String puid;
    private String otaVersion;
    private ActivationStatus activationStatus;
    private ConnectStatus connectStatus;
    private List<ParamStatus> params;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date registrationTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date activeTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public String getOtaVersion() {
        return otaVersion;
    }

    public void setOtaVersion(String otaVersion) {
        this.otaVersion = otaVersion;
    }

    public ActivationStatus getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatus activationStatus) {
        this.activationStatus = activationStatus;
    }

    public ConnectStatus getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(ConnectStatus connectStatus) {
        this.connectStatus = connectStatus;
    }

    public List<ParamStatus> getParams() {
        return params;
    }

    public void setParams(List<ParamStatus> params) {
        this.params = params;
    }
}
