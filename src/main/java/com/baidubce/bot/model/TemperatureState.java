package com.baidubce.iot.dueros.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
public class TemperatureState {
    private Mode mode;
    private TemperatureValue temperature;

    public TemperatureState(TemperatureValue t) {
        this.mode = new Mode();
        this.temperature = t;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Mode {
        private String value = "AUTO";
    }
}
