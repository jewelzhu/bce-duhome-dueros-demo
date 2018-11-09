package com.baidubce.iot.duhome.demo.dueros.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "header.name")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = TurnOnRequest.class, name = "TurnOnRequest"),
//        @JsonSubTypes.Type(value = TurnOffRequest.class, name = "TurnOffRequest"),
//        @JsonSubTypes.Type(value = DiscoverAppliancesRequest.class, name = "DiscoverAppliancesRequest"),
//        @JsonSubTypes.Type(value = IncrementBrightnessPercentageRequest.class, name = "IncrementBrightnessPercentageRequest"),
//})
public abstract class BotData<T extends Payload> {
    private Header header;
    private T payload;
}
