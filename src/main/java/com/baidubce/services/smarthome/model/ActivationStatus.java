package com.baidubce.services.smarthome.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represent device's activation status
 */
public enum ActivationStatus {

    INACTIVE(0),

    ACTIVE(1),

    ACTIVATING(3);

    private int value;

    @JsonValue
    public int getValue() {
        return value;
    }

    ActivationStatus(int value) {
        this.value = value;
    }
}
