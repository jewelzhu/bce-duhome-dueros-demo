package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;

/**
 * Created by shikaiyuan on 2018/10/24.
 */
public class IntStatus extends AbstractBceResponse {
    private int status;

    public IntStatus() {
        this(-1);
    }

    public IntStatus(int status) {
        this.status = status;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
