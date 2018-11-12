package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by shikaiyuan on 2018/10/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentDeviceMetadata extends AbstractBceResponse {
    private String uuid;
    private List<ProductSchemaProperty> metadataList;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<ProductSchemaProperty> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(List<ProductSchemaProperty> metadataList) {
        this.metadataList = metadataList;
    }
}
