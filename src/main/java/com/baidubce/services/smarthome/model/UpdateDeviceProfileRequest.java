package com.baidubce.services.smarthome.model;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represent the request for updating device's profile
 */
public class UpdateDeviceProfileRequest extends SmarthomeRequestImpl {
    private JsonNode deviceParas;

    public JsonNode getDeviceParas() {
        return deviceParas;
    }

    public void setDeviceParas(JsonNode deviceParas) {
        this.deviceParas = deviceParas;
    }
}
