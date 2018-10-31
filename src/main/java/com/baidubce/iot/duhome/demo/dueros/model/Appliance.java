package com.baidubce.iot.duhome.demo.dueros.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Appliance {
    private List<String> actions;
    private List<String> applianceTypes;
    private String applianceId;
    private String manufacturerName;
    @JsonProperty("isReachable")
    private boolean isReachable;
    private String friendlyName; // 设备名称，会与用户口头指令做匹配
    private String friendlyDescription;
    private String version;
    private String modelName;
}
