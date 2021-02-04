package com.nepxion.discovery.plugin.strategy.service.context;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedHashMap;
import java.util.Map;

public class RpcStrategyContext {
    private static final ThreadLocal<RpcStrategyContext> THREAD_LOCAL = ThreadLocal.withInitial(RpcStrategyContext::new);

    private Map<String, Object> attributes = new LinkedHashMap<>();

    public static RpcStrategyContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    public RpcStrategyContext add(String key, Object value) {
        attributes.put(key, value);
        return this;
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    public RpcStrategyContext remove(String key) {
        attributes.remove(key);
        return this;
    }

    public RpcStrategyContext clear() {
        attributes.clear();
        return this;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}