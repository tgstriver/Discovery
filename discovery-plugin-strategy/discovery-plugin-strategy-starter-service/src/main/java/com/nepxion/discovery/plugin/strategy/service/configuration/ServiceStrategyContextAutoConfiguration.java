package com.nepxion.discovery.plugin.strategy.service.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class ServiceStrategyContextAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ServiceStrategyContextHolder serviceStrategyContextHolder() {
        return new ServiceStrategyContextHolder();
    }
}