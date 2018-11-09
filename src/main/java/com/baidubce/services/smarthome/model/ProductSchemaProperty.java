package com.baidubce.services.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shikaiyuan on 2018/10/24.
 */
public class ProductSchemaProperty {

    private String name;
    private String displayName;
    private String type;
    private String unit = "";
    private String unitSymbol = "";
    private boolean allowControl = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public void setUnitSymbol(String unitSymbol) {
        this.unitSymbol = unitSymbol;
    }

    public boolean isAllowControl() {
        return allowControl;
    }

    public void setAllowControl(boolean allowControl) {
        this.allowControl = allowControl;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public List<ValueDescription> getValues() {
        return values;
    }

    public void setValues(List<ValueDescription> values) {
        this.values = values;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private String minValue = "";
    private String maxValue = "";
    private List<ValueDescription> values = new ArrayList<ValueDescription>();
    private boolean enable = true;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ValueDescription {

        private String value;
        private String description;

        public ValueDescription() {
            this(null, null);
        }

        public ValueDescription(String value, String description) {
            this.value = value;
            this.description = description;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}
