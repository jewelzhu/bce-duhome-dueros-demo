package com.baidubce.iot.dueros.bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class OAuth2ServerConfig {
    @Value("${dueros.bot.url.pattern:/api/bot}")
    private String botUrlPattern;

    @Configuration
    @EnableAuthorizationServer
    static class OAuthAuthorizationConfig extends AuthorizationServerConfigurerAdapter {
        @Autowired
        DataSource dataSource;

        @Autowired
        UserDetailsService userDetailsService;

        @Bean
        public ApprovalStore approvalStore() {
            return new JdbcApprovalStore(dataSource);
        }
        @Bean
        protected AuthorizationCodeServices authorizationCodeServices() {
            return new JdbcAuthorizationCodeServices(dataSource);
        }
        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.jdbc(dataSource);

        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.approvalStore(approvalStore())                            // oauth_approvals
                    .authorizationCodeServices(authorizationCodeServices())    // oauth_code
                    .tokenStore(tokenStore())                               // oauth_access_token & oauth_refresh_token
                    .userDetailsService(userDetailsService);
        }
    }

    @Configuration
    @EnableResourceServer
    static class OAuthResourceConfig extends ResourceServerConfigurerAdapter {

        @Value("${dueros.bot.url.pattern:/api/bot}")
        private String botUrlPattern;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("MyResource");
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/**").access("#oauth2.hasScope('read')")
                    .antMatchers(HttpMethod.POST, botUrlPattern).access("#oauth2.hasScope('write')");
        }
    }
}