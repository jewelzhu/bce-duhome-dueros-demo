package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;

import java.util.Map;

/**
 * epresent the response for activating c2c device
 */
public class ActiveC2cDeviceResponse extends AbstractBceResponse {
    private Map<String, String> deviceIdMap;
    private String errorMessage;
    private int errorCode;

    public Map<String, String> getDeviceIdMap() {
        return deviceIdMap;
    }

    public void setDeviceIdMap(Map<String, String> deviceIdMap) {
        this.deviceIdMap = deviceIdMap;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
