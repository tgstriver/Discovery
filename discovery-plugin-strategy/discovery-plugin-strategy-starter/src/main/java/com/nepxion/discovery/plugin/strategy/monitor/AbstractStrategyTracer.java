package com.nepxion.discovery.plugin.strategy.monitor;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public abstract class AbstractStrategyTracer<S> implements StrategyTracer {

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired
    protected StrategyMonitorContext strategyMonitorContext;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_ENABLED + ":false}")
    protected Boolean tracerEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SEPARATE_SPAN_ENABLED + ":true}")
    protected Boolean tracerSeparateSpanEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_RULE_OUTPUT_ENABLED + ":true}")
    protected Boolean tracerRuleOutputEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SPAN_VALUE + ":" + DiscoveryConstant.SPAN_VALUE + "}")
    protected String tracerSpanValue;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SPAN_TAG_PLUGIN_VALUE + ":" + DiscoveryConstant.SPAN_TAG_PLUGIN_VALUE + "}")
    protected String tracerSpanPluginValue;

    @Override
    public void spanBuild() {
        if (!tracerEnabled) {
            return;
        }

        if (!tracerSeparateSpanEnabled) {
            return;
        }

        S span = this.buildSpan();

        StrategyTracerContext.getCurrentContext().setSpan(span);
    }

    @Override
    public void spanOutput(Map<String, String> contextMap) {
        if (!tracerEnabled) {
            return;
        }

        S span = this.getCurrentSpan();
        if (span == null) {
            // LOG.error("Span not found in context to trace put");

            return;
        }

        if (MapUtils.isNotEmpty(contextMap)) {
            for (Map.Entry<String, String> entry : contextMap.entrySet()) {
                this.outputSpan(span, entry.getKey(), entry.getValue());
            }
        }

        Map<String, String> customizationMap = strategyMonitorContext.getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                this.outputSpan(span, entry.getKey(), entry.getValue());
            }
        }

        if (tracerSeparateSpanEnabled) {
            this.outputSpan(span, DiscoveryConstant.SPAN_TAG_PLUGIN_NAME, tracerSpanPluginValue);
        }

        this.outputSpan(span, DiscoveryConstant.TRACE_ID, this.toTraceId(span));
        this.outputSpan(span, DiscoveryConstant.SPAN_ID, this.toSpanId(span));
        this.outputSpan(span, DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        this.outputSpan(span, DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            this.outputSpan(span, DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }

        this.outputSpan(span, DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        this.outputSpan(span, DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        this.outputSpan(span, DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        this.outputSpan(span, DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
        this.outputSpan(span, DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment());
        this.outputSpan(span, DiscoveryConstant.N_D_SERVICE_ZONE, pluginAdapter.getZone());

        if (tracerRuleOutputEnabled) {
            String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
            if (StringUtils.isNotEmpty(routeVersion)) {
                this.outputSpan(span, DiscoveryConstant.N_D_VERSION, routeVersion);
            }
            String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
            if (StringUtils.isNotEmpty(routeRegion)) {
                this.outputSpan(span, DiscoveryConstant.N_D_REGION, routeRegion);
            }
            String routeEnvironment = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT);
            if (StringUtils.isNotEmpty(routeEnvironment)) {
                this.outputSpan(span, DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
            }
            String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
            if (StringUtils.isNotEmpty(routeAddress)) {
                this.outputSpan(span, DiscoveryConstant.N_D_ADDRESS, routeAddress);
            }
            String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
            if (StringUtils.isNotEmpty(routeVersionWeight)) {
                this.outputSpan(span, DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
            }
            String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
            if (StringUtils.isNotEmpty(routeRegionWeight)) {
                this.outputSpan(span, DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
            }
            String idBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ID_BLACKLIST);
            if (StringUtils.isNotEmpty(idBlacklist)) {
                this.outputSpan(span, DiscoveryConstant.N_D_ID_BLACKLIST, idBlacklist);
            }
            String addressBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_BLACKLIST);
            if (StringUtils.isNotEmpty(addressBlacklist)) {
                this.outputSpan(span, DiscoveryConstant.N_D_ADDRESS_BLACKLIST, addressBlacklist);
            }
        }
    }

    @Override
    public void spanError(Throwable e) {
        if (!tracerEnabled) {
            return;
        }

        if (!tracerSeparateSpanEnabled) {
            return;
        }

        S span = this.getCurrentSpan();
        if (span == null) {
            // LOG.error("Span not found in context to trace error");

            return;
        }

        this.errorSpan(span, e);
    }

    @Override
    public void spanFinish() {
        if (!tracerEnabled) {
            return;
        }

        if (!tracerSeparateSpanEnabled) {
            return;
        }

        S span = this.getCurrentSpan();
        if (span != null) {
            this.finishSpan(span);
        } else {
            // LOG.error("Span not found in context to trace clear");
        }

        StrategyTracerContext.clearCurrentContext();
    }

    @SuppressWarnings("unchecked")
    private S getCurrentSpan() {
        return tracerSeparateSpanEnabled ? (S) StrategyTracerContext.getCurrentContext().getSpan() : this.getActiveSpan();
    }

    @Override
    public String getTraceId() {
        if (!tracerEnabled) {
            return null;
        }

        S span = getCurrentSpan();
        if (span != null) {
            return toTraceId(span);
        }

        return null;
    }

    @Override
    public String getSpanId() {
        if (!tracerEnabled) {
            return null;
        }

        S span = this.getCurrentSpan();
        if (span != null) {
            return this.toSpanId(span);
        }

        return null;
    }

    protected abstract S buildSpan();

    protected abstract void outputSpan(S span, String key, String value);

    protected abstract void errorSpan(S span, Throwable e);

    protected abstract void finishSpan(S span);

    protected abstract S getActiveSpan();

    protected abstract String toTraceId(S span);

    protected abstract String toSpanId(S span);
}