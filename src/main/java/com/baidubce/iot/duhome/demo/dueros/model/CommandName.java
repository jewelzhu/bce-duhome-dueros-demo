package com.baidubce.iot.duhome.demo.dueros.model;

public enum CommandName {
    DiscoverAppliancesRequest,
    TurnOnRequest,
    TurnOffRequest,
    IncrementBrightnessPercentageRequest,
    DecrementBrightnessPercentageRequest,
    SetBrightnessPercentageRequest,
    SetColorRequest,

    DiscoverAppliancesResponse,
    TurnOnConfirmation,
    TurnOffConfirmation,
    IncrementBrightnessPercentageConfirmation,
    DecrementBrightnessPercentageConfirmation,
    SetBrightnessPercentageConfirmation,
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
            case SetBrightnessPercentageRequest:
                return SetBrightnessPercentageConfirmation;
            case SetColorRequest:
                return SetColorConfirmation;
            default:
                return null;
        }
    }
}
