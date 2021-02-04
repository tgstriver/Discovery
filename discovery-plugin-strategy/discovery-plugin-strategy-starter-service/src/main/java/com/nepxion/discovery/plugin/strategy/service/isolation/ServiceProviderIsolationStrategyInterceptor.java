package com.nepxion.discovery.plugin.strategy.service.isolation;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceProviderIsolationStrategyInterceptor extends AbstractInterceptor {

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected ServiceStrategyContextHolder serviceStrategyContextHolder;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String groupHeader = serviceStrategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP);
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        if (!StringUtils.equals(groupHeader, group)) {
            throw new DiscoveryException("Reject to invoke because of isolation with different service group for serviceId=" + serviceId);
        }

        return invocation.proceed();
    }
}