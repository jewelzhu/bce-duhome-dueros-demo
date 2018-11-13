package com.baidubce.iot.dueros.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeTemperatureConfirmationPayload extends PayloadWithSingleAppliance{
    private TemperatureState previousState;
    private TemperatureValue temperature;
}
