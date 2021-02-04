package com.nepxion.discovery.plugin.strategy.monitor;

import java.util.Map;

public interface StrategyTracer {

    void spanBuild();

    void spanOutput(Map<String, String> contextMap);

    void spanError(Throwable e);

    void spanFinish();

    String getTraceId();

    String getSpanId();
}