package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by shikaiyuan on 2018/10/24.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentActiveResultResponse extends AbstractBceResponse {
    private String puid;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }
}
