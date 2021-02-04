package com.nepxion.discovery.plugin.strategy.service.monitor;

import org.aopalliance.intercept.MethodInvocation;

public interface ServiceStrategyMonitor {

    void monitor(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation);

    void monitor(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Object returnValue);

    void error(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Throwable e);

    void release(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation);
}