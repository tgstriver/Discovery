package com.nepxion.discovery.plugin.strategy.service.monitor;

import org.aopalliance.intercept.MethodInvocation;

import java.util.Map;

public interface ServiceStrategyMonitorAdapter {

    Map<String, String> getCustomizationMap(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation,
                                            Map<String, Object> parameterMap, Object returnValue);
}