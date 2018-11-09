package com.baidubce.iot.duhome.demo.dueros.model;

public enum CommandName {
    DiscoverAppliancesRequest,
    TurnOnRequest,
    TurnOffRequest,
    IncrementBrightnessPercentageRequest,
    DecrementBrightnessPercentageRequest,
    SetColorRequest,

    DiscoverAppliancesResponse,
    TurnOnConfirmation,
    TurnOffConfirmation,
    IncrementBrightnessPercentageConfirmation,
    DecrementBrightnessPercentageConfirmation,
    SetColorConfirmation;

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
            case SetColorRequest:
                return SetColorConfirmation;
            default:
                return null;
        }
    }
}
