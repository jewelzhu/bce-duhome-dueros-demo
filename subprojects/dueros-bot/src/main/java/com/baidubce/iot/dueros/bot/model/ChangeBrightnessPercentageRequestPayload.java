package com.baidubce.iot.dueros.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ChangeBrightnessPercentageRequestPayload extends PayloadWithSingleAppliance {
    private Percentage deltaPercentage;
}
