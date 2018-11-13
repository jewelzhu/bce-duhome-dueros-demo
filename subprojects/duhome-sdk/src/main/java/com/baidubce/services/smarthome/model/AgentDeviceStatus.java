package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by shikaiyuan on 2018/10/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentDeviceStatus extends AbstractBceResponse {
    private String uuid;
    private int connectStatus;
    private List<ParamStatus> params;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(int connectStatus) {
        this.connectStatus = connectStatus;
    }

    public List<ParamStatus> getParams() {
        return params;
    }

    public void setParams(List<ParamStatus> params) {
        this.params = params;
    }
}
