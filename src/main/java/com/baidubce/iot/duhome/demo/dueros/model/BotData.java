package com.baidubce.iot.duhome.demo.dueros.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BotData {
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
        private List<Appliance> discoveredAppliances;
        private List<Group> discoveredGroups;
    }
}
