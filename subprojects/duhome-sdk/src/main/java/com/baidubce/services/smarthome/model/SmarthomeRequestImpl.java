package com.baidubce.services.smarthome.model;

import com.baidubce.auth.BceCredentials;
import com.baidubce.model.AbstractBceRequest;

/**
 * Base request of smart home
 */
public class SmarthomeRequestImpl extends AbstractBceRequest {
    public AbstractBceRequest withRequestCredentials(BceCredentials credentials) {
        setRequestCredentials(credentials);
        return this;
    }
}
