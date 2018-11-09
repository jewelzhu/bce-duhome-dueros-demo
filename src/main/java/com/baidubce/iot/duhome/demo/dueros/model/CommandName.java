package com.baidubce.iot.duhome.demo.dueros.model;

public enum CommandName {
    DiscoverAppliancesRequest,
    TurnOnRequest,
    TurnOffRequest,
    IncrementBrightnessPercentageRequest,
    DecrementBrightnessPercentageRequest,
    SetBrightnessPercentageRequest,
    SetColorRequest,
    IncrementTemperatureRequest,
    DecrementTemperatureRequest,

    DiscoverAppliancesResponse,
    TurnOnConfirmation,
    TurnOffConfirmation,
    IncrementBrightnessPercentageConfirmation,
    DecrementBrightnessPercentageConfirmation,
    SetBrightnessPercentageConfirmation,
    SetColorConfirmation,
    IncrementTemperatureConfirmation,
    DecrementTemperatureConfirmation;

    public static CommandName getConfirmationOf(CommandName name) {
        switch (name) {
            case TurnOnRequest:
                return TurnOnConfirmation;
            case TurnOffRequest:
                return TurnOffConfirmation;
            case IncrementBrightnessPercentageRequest:
                return IncrementBrightnessPercentageConfirmation;
            case DecrementBrightnessPercentageRequest:
                return DecrementBrightnessPercentageConfirmation;
            case SetBrightnessPercentageRequest:
                return SetBrightnessPercentageConfirmation;
            case SetColorRequest:
                return SetColorConfirmation;
            case IncrementTemperatureRequest:
                return IncrementTemperatureConfirmation;
            case DecrementTemperatureRequest:
                return DecrementTemperatureConfirmation;
            default:
                return null;
        }
    }
}
