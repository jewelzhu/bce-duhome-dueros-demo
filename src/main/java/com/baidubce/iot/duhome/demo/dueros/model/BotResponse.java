package com.baidubce.iot.duhome.demo.dueros.model;

//    {
//        "header": {
//        "namespace": "DuerOS.ConnectedHome.Discovery",
//                "name": "DiscoverAppliancesResponse",
//                "messageId": "ff746d98-ab02-4c9e-9d0d-b44711658414",
//                "payloadVersion": "1"
//    },
//        "payload": {
//        "discoveredAppliances": [
//        {
//            "actions": [
//            "turnOn",
//                    "turnOff",
//                    "incrementBrightnessPercentage",
//                    "decrementBrightnessPercentage"
//                ],
//            "applianceTypes": [
//            "LIGHT"
//                ],
//            "additionalApplianceDetails": {
//            "extraDetail1": "optionalDetailForSkillAdapterToReferenceThisDevice",
//                    "extraDetail2": "There can be multiple entries",
//                    "extraDetail3": "but they should only be used for reference purposes",
//                    "extraDetail4": "This is not a suitable place to maintain current device state"
//        },
//            "applianceId": "uniqueLightDeviceId",
//                "friendlyDescription": "展现给用户的详细介绍",
//                "friendlyName": "卧室的灯",
//                "isReachable": true,
//                "manufacturerName": "设备制造商的名称",
//                "modelName": "fancyLight",
//                "version": "your software version number here.",
//                "attributes": [
//            {
//                "name": "name",
//                    "value": "卧室的灯",
//                    "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 10
//            },
//            {
//                "name": "connectivity",
//                    "value": "REACHABLE",
//                    "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 10
//            },
//            {
//                "name": "color",
//                    "value": {
//                "hue": 350.5,
//                        "saturation": 0.7138,
//                        "brightness": 0.6524
//            },
//                "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 10
//            },
//            {
//                "name": "powerState",
//                    "value": "ON",
//                    "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 0
//            },
//            {
//                "name": "brightness",
//                    "value": "50",
//                    "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 100
//            }
//                ]
//        },
//        {
//            "actions": [
//            "turnOn",
//                    "turnOff"
//                ],
//            "applianceTypes": [
//            "CURTAIN"
//                ],
//            "additionalApplianceDetails": {
//            "extraDetail1": "optionalDetailForSkillAdapterToReferenceThisDevice",
//                    "extraDetail2": "There can be multiple entries",
//                    "extraDetail3": "but they should only be used for reference purposes",
//                    "extraDetail4": "This is not a suitable place to maintain current device state"
//        },
//            "applianceId": "uniqueSwitchDeviceId",
//                "friendlyDescription": "展现给用户的详细介绍",
//                "friendlyName": "卧室的窗帘",
//                "isReachable": true,
//                "manufacturerName": "设备制造商的名称",
//                "modelName": "fancyCurtain",
//                "version": "your software version number here",
//                "attributes": [
//            {
//                "name": "name",
//                    "value": "卧室的窗帘",
//                    "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 10
//            },
//            {
//                "name": "connectivity",
//                    "value": "REACHABLE",
//                    "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 10
//            },
//            {
//                "name": "turnOnState",
//                    "value": "ON",
//                    "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 10
//            }
//                ]
//        },
//        {
//            "actions": [
//            "turnOn",
//                    "turnOff"
//                ],
//            "applianceTypes": [
//            "SCENE_TRIGGER"
//                ],
//            "additionalApplianceDetails": {
//            "extraDetail1": "detail about the scene",
//                    "extraDetail2": "another detail about scene",
//                    "extraDetail3": "only be used for reference purposes"
//        },
//            "applianceId": "uniqueDeviceId",
//                "friendlyDescription": "来自设备商的场景",
//                "friendlyName": "回家模式",
//                "isReachable": true,
//                "manufacturerName": "yourManufacturerName",
//                "modelName": "提供场景的设备型号",
//                "version": "your software version number here",
//                "attributes": [
//            {
//                "name": "name",
//                    "value": "回家模式",
//                    "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 10
//            },
//            {
//                "name": "turnOnState",
//                    "value": "ON",
//                    "scale": "",
//                    "timestampOfSample": 1496741861,
//                    "uncertaintyInMilliseconds": 0
//            }
//                ]
//        }
//        ],
//        "discoveredGroups": [
//        {
//            "groupName": "客厅",
//                "applianceIds": [
//            "001",
//                    "002",
//                    "003"
//                ],
//            "groupNotes": "客厅照明分组控制",
//                "additionalGroupDetails": {
//            "extraDetail1": "detail about the group",
//                    "extraDetail2": "another detail about group",
//                    "extraDetail3": "only be used for reference group"
//        }
//        },
//        {
//            "groupName": "卧室",
//                "applianceIds": [
//            "004",
//                    "005",
//                    "006"
//                ],
//            "groupNotes": "卧室空调的分组控制",
//                "additionalGroupDetails": {
//            "extraDetail1": "detail about the group",
//                    "extraDetail2": "another detail about group",
//                    "extraDetail3": "only be used for reference group"
//        }
//        }
//        ]
//    }
//    }

//{
//	"header": {
//		"namespace": "DuerOS.ConnectedHome.Control",
//		"name": "TurnOffConfirmation",
//		"messageId": "557e8e0ec12d4    e5db7959dfcc87427f0_0#1_0_Smarthome_5bd1615bdb89e4.51232751",
//		"payloadVersion": "1"
//	},
//	"payload": []
//}
public class BotResponse {



}
