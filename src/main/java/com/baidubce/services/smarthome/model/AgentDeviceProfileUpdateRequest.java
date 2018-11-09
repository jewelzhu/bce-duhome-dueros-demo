package com.baidubce.services.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by shikaiyuan on 2018/10/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentDeviceProfileUpdateRequest extends SmarthomeRequestImpl {
    private JsonNode deviceParas;

    public AgentDeviceProfileUpdateRequest(JsonNode deviceParas) {
        if (deviceParas == null) {
            throw new IllegalArgumentException("deviceParas can not be null");
        }
        this.deviceParas = deviceParas;
    }

    public JsonNode getDeviceParas() {

        return deviceParas;
    }

    public void setDeviceParas(JsonNode deviceParas) {
        this.deviceParas = deviceParas;
    }
}
