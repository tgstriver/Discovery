package com.nepxion.discovery.plugin.strategy.monitor;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class StrategyMonitor {

    @Autowired(required = false)
    protected StrategyLogger strategyLogger;

    @Autowired(required = false)
    protected StrategyTracer strategyTracer;

    public void loggerOutput() {
        if (strategyLogger != null) {
            strategyLogger.loggerOutput();
        }
    }

    public void loggerClear() {
        if (strategyLogger != null) {
            strategyLogger.loggerClear();
        }
    }

    public void loggerDebug() {
        if (strategyLogger != null) {
            strategyLogger.loggerDebug();
        }
    }

    public void spanBuild() {
        if (strategyTracer != null) {
            strategyTracer.spanBuild();
        }
    }

    public void spanOutput(Map<String, String> contextMap) {
        if (strategyTracer != null) {
            strategyTracer.spanOutput(contextMap);
        }
    }

    public void spanError(Throwable e) {
        if (strategyTracer != null) {
            strategyTracer.spanError(e);
        }
    }

    public void spanFinish() {
        if (strategyTracer != null) {
            strategyTracer.spanFinish();
        }
    }

    public StrategyLogger getStrategyLogger() {
        return strategyLogger;
    }

    public StrategyTracer getStrategyTracer() {
        return strategyTracer;
    }
}