package com.nepxion.discovery.plugin.strategy.constant;

public interface StrategyConstant {

    String SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED = "spring.application.strategy.control.enabled";
    String SPRING_APPLICATION_STRATEGY_ZONE_AVOIDANCE_RULE_ENABLED = "spring.application.strategy.zone.avoidance.rule.enabled";
    String SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED = "spring.application.strategy.hystrix.threadlocal.supported";
    String SPRING_APPLICATION_STRATEGY_CONSUMER_ISOLATION_ENABLED = "spring.application.strategy.consumer.isolation.enabled";
    String SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED = "spring.application.strategy.provider.isolation.enabled";
    String SPRING_APPLICATION_STRATEGY_ENVIRONMENT_ROUTE = "spring.application.strategy.environment.route";
    String SPRING_APPLICATION_STRATEGY_ENVIRONMENT_ROUTE_VALUE = "common";
    String SPRING_APPLICATION_STRATEGY_ZONE_AFFINITY_ENABLED = "spring.application.strategy.zone.affinity.enabled";
    String SPRING_APPLICATION_STRATEGY_ZONE_ROUTE_ENABLED = "spring.application.strategy.zone.route.enabled";
    String SPRING_APPLICATION_STRATEGY_VERSION_FAILOVER_ENABLED = "spring.application.strategy.version.failover.enabled";
    String SPRING_APPLICATION_STRATEGY_VERSION_PREFER_ENABLED = "spring.application.strategy.version.prefer.enabled";

    String SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED = "spring.application.strategy.rest.intercept.enabled";
    String SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_DEBUG_ENABLED = "spring.application.strategy.rest.intercept.debug.enabled";
    String SPRING_APPLICATION_STRATEGY_FEIGN_CORE_HEADER_TRANSMISSION_ENABLED = "spring.application.strategy.feign.core.header.transmission.enabled";
    String SPRING_APPLICATION_STRATEGY_REST_TEMPLATE_CORE_HEADER_TRANSMISSION_ENABLED = "spring.application.strategy.rest.template.core.header.transmission.enabled";
    String SPRING_APPLICATION_STRATEGY_CONTEXT_REQUEST_HEADERS = "spring.application.strategy.context.request.headers";
    String SPRING_APPLICATION_STRATEGY_BUSINESS_REQUEST_HEADERS = "spring.application.strategy.business.request.headers";

    String SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED = "spring.application.strategy.monitor.enabled";
    String SPRING_APPLICATION_STRATEGY_LOGGER_ENABLED = "spring.application.strategy.logger.enabled";
    String SPRING_APPLICATION_STRATEGY_LOGGER_MDC_KEY_SHOWN = "spring.application.strategy.logger.mdc.key.shown";
    String SPRING_APPLICATION_STRATEGY_LOGGER_DEBUG_ENABLED = "spring.application.strategy.logger.debug.enabled";
    String SPRING_APPLICATION_STRATEGY_TRACER_ENABLED = "spring.application.strategy.tracer.enabled";
    String SPRING_APPLICATION_STRATEGY_TRACER_SEPARATE_SPAN_ENABLED = "spring.application.strategy.tracer.separate.span.enabled";
    String SPRING_APPLICATION_STRATEGY_TRACER_RULE_OUTPUT_ENABLED = "spring.application.strategy.tracer.rule.output.enabled";
    String SPRING_APPLICATION_STRATEGY_TRACER_EXCEPTION_DETAIL_OUTPUT_ENABLED = "spring.application.strategy.tracer.exception.detail.output.enabled";
    String SPRING_APPLICATION_STRATEGY_TRACER_METHOD_CONTEXT_OUTPUT_ENABLED = "spring.application.strategy.tracer.method.context.output.enabled";
    String SPRING_APPLICATION_STRATEGY_TRACER_SPAN_VALUE = "spring.application.strategy.tracer.span.value";
    String SPRING_APPLICATION_STRATEGY_TRACER_SPAN_TAG_PLUGIN_VALUE = "spring.application.strategy.tracer.span.tag.plugin.value";
}