package com.baidubce.iot.dueros.demo.config;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.smarthome.SmarthomeAgentClient;
import com.baidubce.services.smarthome.SmarthomeClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DuhomeConfig {
    @Value("${bce.ak}")
    @Setter
    private String ak;

    @Value("${bce.sk}")
    @Setter
    private String sk;

    @Bean
    public SmarthomeClient duhomeClient() {
        BceClientConfiguration bceClientConfiguration = new BceClientConfiguration()
                .withCredentials(new DefaultBceCredentials(ak, sk)).withEndpoint("smarthome.gz.baidubce.com");
        return new SmarthomeClient(bceClientConfiguration);
    }

    @Bean
    public SmarthomeAgentClient duhomeAgentClient() {
        BceClientConfiguration bceClientConfiguration = new BceClientConfiguration()
                .withCredentials(new DefaultBceCredentials(ak, sk)).withEndpoint("smarthome.gz.baidubce.com");
        return new SmarthomeAgentClient(bceClientConfiguration);
    }
}
