package com.baidubce.services.smarthome.model;

import java.util.List;

/**
 * Represent the request for creating a ota job
 */
public class OtaJobRequest extends SmarthomeRequestImpl {
    private List<String> puidList;

    public List<String> getPuidList() {
        return puidList;
    }

    public void setPuidList(List<String> puidList) {
        this.puidList = puidList;
    }
}
