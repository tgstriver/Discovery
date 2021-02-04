package com.nepxion.discovery.plugin.strategy.configuration;

import feign.Feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.expression.TypeComparator;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.plugin.strategy.adapter.DefaultDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DefaultStrategyVersionFilterAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyVersionFilterAdapter;
import com.nepxion.discovery.plugin.strategy.aop.FeignStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.aop.RestTemplateStrategyBeanPostProcessor;
import com.nepxion.discovery.plugin.strategy.aop.RestTemplateStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.condition.DefaultStrategyTypeComparor;
import com.nepxion.discovery.plugin.strategy.condition.ExpressionStrategyCondition;
import com.nepxion.discovery.plugin.strategy.condition.StrategyCondition;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.filter.StrategyVersionFilter;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryAntPathMatcherStrategy;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryMatcherStrategy;
import com.nepxion.discovery.plugin.strategy.monitor.DefaultStrategyLogger;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyLogger;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyMonitorContext;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

@Configuration
@RibbonClients(defaultConfiguration = {StrategyLoadBalanceConfiguration.class})
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class StrategyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryEnabledAdapter discoveryEnabledAdapter() {
        return new DefaultDiscoveryEnabledAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryMatcherStrategy discoveryMatcherStrategy() {
        return new DiscoveryAntPathMatcherStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyVersionFilter strategyVersionFilter() {
        return new StrategyVersionFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyVersionFilterAdapter strategyVersionFilterAdapter() {
        return new DefaultStrategyVersionFilterAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED)
    public StrategyMonitorContext strategyMonitorContext() {
        return new StrategyMonitorContext();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED)
    public StrategyLogger strategyLogger() {
        return new DefaultStrategyLogger();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyWrapper strategyWrapper() {
        return new StrategyWrapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public TypeComparator strategyTypeComparator() {
        return new DefaultStrategyTypeComparor();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyCondition strategyCondition() {
        return new ExpressionStrategyCondition();
    }

    @ConditionalOnClass(Feign.class)
    protected static class FeignStrategyConfiguration {
        @Autowired
        private ConfigurableEnvironment environment;

        @Bean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
        public FeignStrategyInterceptor feignStrategyInterceptor() {
            String contextRequestHeaders = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTEXT_REQUEST_HEADERS);
            String businessRequestHeaders = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_BUSINESS_REQUEST_HEADERS);

            return new FeignStrategyInterceptor(contextRequestHeaders, businessRequestHeaders);
        }
    }

    @ConditionalOnClass(RestTemplate.class)
    protected static class RestTemplateStrategyConfiguration {

        @Autowired
        private ConfigurableEnvironment environment;

        @Bean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
        public RestTemplateStrategyInterceptor restTemplateStrategyInterceptor() {
            String contextRequestHeaders = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTEXT_REQUEST_HEADERS);
            String businessRequestHeaders = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_BUSINESS_REQUEST_HEADERS);

            return new RestTemplateStrategyInterceptor(contextRequestHeaders, businessRequestHeaders);
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
        public RestTemplateStrategyBeanPostProcessor restTemplateStrategyBeanPostProcessor() {
            return new RestTemplateStrategyBeanPostProcessor();
        }
    }
}