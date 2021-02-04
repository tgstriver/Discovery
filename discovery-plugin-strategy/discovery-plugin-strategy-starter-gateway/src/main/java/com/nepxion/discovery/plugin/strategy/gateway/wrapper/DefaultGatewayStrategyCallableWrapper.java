package com.nepxion.discovery.plugin.strategy.gateway.wrapper;

import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContext;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;
import org.springframework.web.server.ServerWebExchange;

import java.util.concurrent.Callable;

public class DefaultGatewayStrategyCallableWrapper implements GatewayStrategyCallableWrapper {

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        ServerWebExchange exchange = GatewayStrategyContext.getCurrentContext().getExchange();
        Object span = StrategyTracerContext.getCurrentContext().getSpan();

        return () -> {
            try {
                GatewayStrategyContext.getCurrentContext().setExchange(exchange);
                StrategyTracerContext.getCurrentContext().setSpan(span);
                return callable.call();
            } finally {
                GatewayStrategyContext.clearCurrentContext();
                StrategyTracerContext.clearCurrentContext();
            }
        };
    }
}