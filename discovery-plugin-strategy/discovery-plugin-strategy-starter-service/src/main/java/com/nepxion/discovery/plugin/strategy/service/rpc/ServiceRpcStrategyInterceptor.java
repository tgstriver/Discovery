package com.nepxion.discovery.plugin.strategy.service.rpc;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.ClassUtil;
import com.nepxion.discovery.plugin.strategy.service.context.RpcStrategyContext;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Map;

public class ServiceRpcStrategyInterceptor extends AbstractInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> clazz = super.getMethod(invocation).getDeclaringClass();
        String methodName = super.getMethodName(invocation);
        String[] methodParameterNames = super.getMethodParameterNames(invocation);
        Object[] arguments = super.getArguments(invocation);
        Map<String, Object> parameterMap = ClassUtil.getParameterMap(methodParameterNames, arguments);

        RpcStrategyContext context = RpcStrategyContext.getCurrentContext();
        context.add(DiscoveryConstant.CLASS, clazz);
        context.add(DiscoveryConstant.METHOD, methodName);
        context.add(DiscoveryConstant.PARAMETER_MAP, parameterMap);

        try {
            return invocation.proceed();
        } finally {
            RpcStrategyContext.clearCurrentContext();
        }
    }
}