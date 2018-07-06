package com.sdy.rest.config;

import com.sdy.rest.modular.auth.filter.AuthFilter;
import com.sdy.rest.modular.auth.security.impl.Base64SecurityAction;
import com.sdy.rest.config.properties.RestProperties;
import com.sdy.rest.modular.auth.security.DataSecurityAction;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * web配置
 */
@Configuration
public class WebConfig {

    @Bean
    @ConditionalOnProperty(prefix = RestProperties.REST_PREFIX, name = "auth-open", havingValue = "true", matchIfMissing = true)
    public AuthFilter jwtAuthenticationTokenFilter() {
        return new AuthFilter();
    }

    @Bean
    public DataSecurityAction dataSecurityAction() {
        return new Base64SecurityAction();
    }
}
