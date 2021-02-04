package com.nepxion.discovery.plugin.strategy.opentracing.monitor;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.monitor.AbstractStrategyTracer;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class OpenTracingStrategyTracer extends AbstractStrategyTracer<Span> {

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_EXCEPTION_DETAIL_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerExceptionDetailOutputEnabled;

    @Autowired
    private Tracer tracer;

    @Override
    protected Span buildSpan() {
        return tracer.buildSpan(tracerSpanValue).start();
    }

    @Override
    protected void outputSpan(Span span, String key, String value) {
        span.setTag(key, value);
    }

    @Override
    protected void errorSpan(Span span, Throwable e) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DiscoveryConstant.EVENT, DiscoveryConstant.ERROR);
        if (tracerExceptionDetailOutputEnabled) {
            map.put(DiscoveryConstant.ERROR_OBJECT, ExceptionUtils.getStackTrace(e));
        } else {
            map.put(DiscoveryConstant.ERROR_OBJECT, e);
        }
        span.log(map);
    }

    @Override
    protected void finishSpan(Span span) {
        span.finish();
    }

    @Override
    protected Span getActiveSpan() {
        return tracer.activeSpan();
    }

    @Override
    protected String toTraceId(Span span) {
        return span.context().toTraceId();
    }

    @Override
    protected String toSpanId(Span span) {
        return span.context().toSpanId();
    }
}