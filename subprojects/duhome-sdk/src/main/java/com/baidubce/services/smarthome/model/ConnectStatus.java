package com.baidubce.services.smarthome.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represent device's connect status
 */
public enum ConnectStatus {
    OFFLINE(0),

    ONLINE(1),

    UNKNOWN(2),

    DEACTIVE(3);

    private int value;

    @JsonValue
    public int getValue() {
        return value;
    }

    ConnectStatus(int value) {
        this.value = value;
    }
}
