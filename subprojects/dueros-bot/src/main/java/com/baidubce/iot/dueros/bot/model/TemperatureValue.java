package com.baidubce.iot.dueros.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureValue {
    private double value;
    private String scale;

    public TemperatureValue(double value) {
        this.scale = "CELSIUS";
        this.value = value;
    }
}
