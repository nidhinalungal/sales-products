package com.sparksupport.nidhin.sales_products.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig {

    @Value("${security.oauth2.resource.jwt.key-store}")
    private String keyStorePath;

    @Value("${security.oauth2.resource.jwt.key-store-password}")
    private String keyStorePass;

    @Value("${security.oauth2.resource.jwt.key-alias}")
    private String keyPairAlias;

    @Value("${security.oauth2.client.access-token-validity-seconds}")
    private String accessTokenValidity;

    @Value("${security.oauth2.client.refresh-token-validity-seconds}")
    private String refreshTokenValidity;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        CustomJwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource(keyStorePath), keyStorePass.toCharArray()).getKeyPair(keyPairAlias);
        converter.setKeyPair(keyPair);
        return converter;
    }


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }


    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenEnhancer(accessTokenConverter());
        tokenServices.setAccessTokenValiditySeconds(Integer.parseInt(accessTokenValidity));
        tokenServices.setRefreshTokenValiditySeconds(Integer.parseInt(refreshTokenValidity));
        return tokenServices;
    }
}
