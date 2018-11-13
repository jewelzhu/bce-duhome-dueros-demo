package com.baidubce.iot.dueros.bot.config;

import com.baidubce.iot.dueros.bot.filter.DuerosAccessTokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DuerosAccessTokenFilterConfig {
    @Value("${dueros.bot.url.pattern:/api/bot}")
    private String botUrlPattern;

    @Bean
    public FilterRegistrationBean duerosAccessTokenFilter(){
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.addUrlPatterns(botUrlPattern);
        filterRegBean.setFilter(new DuerosAccessTokenFilter());
        filterRegBean.setOrder(Integer.MIN_VALUE);
        return filterRegBean;
    }
}
