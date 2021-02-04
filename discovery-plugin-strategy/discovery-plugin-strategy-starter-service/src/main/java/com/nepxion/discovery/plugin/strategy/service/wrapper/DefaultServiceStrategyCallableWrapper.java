package com.nepxion.discovery.plugin.strategy.service.wrapper;

import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext;
import com.nepxion.discovery.plugin.strategy.service.context.RpcStrategyContext;
import com.nepxion.discovery.plugin.strategy.service.decorator.ServiceStrategyRequestDecoratorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.concurrent.Callable;

public class DefaultServiceStrategyCallableWrapper implements ServiceStrategyCallableWrapper {

    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_REQUEST_DECORATOR_ENABLED + ":true}")
    protected Boolean requestDecoratorEnabled;

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        RequestAttributes originRequestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestDecoratorEnabled) {
            originRequestAttributes = ServiceStrategyRequestDecoratorFactory.decorateRequestAttributes(originRequestAttributes);
        }

        RequestAttributes requestAttributes = originRequestAttributes;
        Map<String, Object> attributes = RpcStrategyContext.getCurrentContext().getAttributes();
        Object span = StrategyTracerContext.getCurrentContext().getSpan();

        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                RestStrategyContext.getCurrentContext().setRequestAttributes(requestAttributes);
                RpcStrategyContext.getCurrentContext().setAttributes(attributes);
                StrategyTracerContext.getCurrentContext().setSpan(span);
                return callable.call();
            } finally {
                RequestContextHolder.resetRequestAttributes();
                RestStrategyContext.clearCurrentContext();
                RpcStrategyContext.clearCurrentContext();
                StrategyTracerContext.clearCurrentContext();
            }
        };
    }
}