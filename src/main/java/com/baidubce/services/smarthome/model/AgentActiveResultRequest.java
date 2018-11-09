package com.baidubce.services.smarthome.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by shikaiyuan on 2018/10/24.
 */
public class AgentActiveResultRequest extends SmarthomeRequestImpl {
    private String uuid;
    private String sid;
    private String deviceId;
    private String companyId;
    private AgentActiveResult result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errMsg;

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public AgentActiveResult getResult() {
        return result;
    }

    public void setResult(AgentActiveResult result) {
        this.result = result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
