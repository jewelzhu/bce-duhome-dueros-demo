package com.baidubce.services.smarthome.model;

/**
 * Created by shikaiyuan on 2018/11/2.
 */
public class AgentDeactivateResultRequest extends SmarthomeRequestImpl {
    private String puid;
    private String customDeviceId;
    private AgentDeactivateResult result;
    private String errMsg;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getCustomDeviceId() {
        return customDeviceId;
    }

    public void setCustomDeviceId(String customDeviceId) {
        this.customDeviceId = customDeviceId;
    }

    public AgentDeactivateResult getResult() {
        return result;
    }

    public void setResult(AgentDeactivateResult result) {
        this.result = result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
