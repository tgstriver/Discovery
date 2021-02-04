package com.nepxion.discovery.plugin.registercenter.nacos.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @version 1.0
 */

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosRibbonClientConfiguration;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;
import com.nepxion.discovery.plugin.registercenter.nacos.decorator.NacosServerListDecorator;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
@AutoConfigureAfter(NacosRibbonClientConfiguration.class)
public class NacosLoadBalanceConfiguration {

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    @Bean
    public ServerList<?> ribbonServerList(IClientConfig config, NacosDiscoveryProperties nacosDiscoveryProperties) {
        NacosServerListDecorator serverList = new NacosServerListDecorator(nacosDiscoveryProperties);
        serverList.initWithNiwsConfig(config);
        serverList.setEnvironment(environment);
        serverList.setLoadBalanceListenerExecutor(loadBalanceListenerExecutor);
        return serverList;
    }
}