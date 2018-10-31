package com.baidubce.iot.duhome.demo.dueros.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * //{
 //    "header": {
 //        "namespace": "DuerOS.ConnectedHome.Discovery",
 //        "name": "DiscoverAppliancesRequest",
 //        "messageId": "6d6d6e14-8aee-473e-8c24-0d31ff9c17a2",
 //        "payloadVersion": "1"
 //    },
 //    "payload": {
 //        "accessToken": "[OAuth Token here]",
 //        "openUid": "27a7d83c2d3cfbad5d387cd35f3ca17b"
 //    }
 //}
 //{
 //	"header": {
 //		"payloadVersion": "1",
 //		"messageId": "557e8e0ec12d4e5db7959dfcc87427f0_0#1_0_Smarthome_5bd1615bdb89e4.51232751",
 //		"name": "TurnOffRequest",
 //		"namespace": "DuerOS.ConnectedHome.Control"
 //	},
 //	"payload": {
 //		"accessToken": "58aaf31cc2fe52a1800804449afe5220d91ee4f7",
 //		"appliance": {
 //			"additionalApplianceDetails": {},
 //			"applianceId": "234567"
 //		}
 //	}
 //}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BotRequest {
    private Header header;
    private Payload payload;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Header {
        private String payloadVersion;
        private String namespace;
        private CommandName name;
        private String messageId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Payload {
        private String accessToken;
        private String openUid;
        private Appliance appliance;
    }
}
