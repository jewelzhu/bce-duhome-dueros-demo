package com.baidubce.iot.duhome.demo.dueros.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HSBColor {
    private double hue;
    private double saturation;
    private double brightness;
}
