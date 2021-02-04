package com.nepxion.discovery.plugin.strategy.zuul.wrapper;

import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;
import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.Callable;

public class DefaultZuulStrategyCallableWrapper implements ZuulStrategyCallableWrapper {

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Map<String, String> headers = RequestContext.getCurrentContext().getZuulRequestHeaders();
        Object span = StrategyTracerContext.getCurrentContext().getSpan();

        return () -> {
            try {
                ZuulStrategyContext.getCurrentContext().setRequest(request);
                ZuulStrategyContext.getCurrentContext().setHeaders(headers);
                StrategyTracerContext.getCurrentContext().setSpan(span);
                return callable.call();
            } finally {
                ZuulStrategyContext.clearCurrentContext();
                StrategyTracerContext.clearCurrentContext();
            }
        };
    }
}