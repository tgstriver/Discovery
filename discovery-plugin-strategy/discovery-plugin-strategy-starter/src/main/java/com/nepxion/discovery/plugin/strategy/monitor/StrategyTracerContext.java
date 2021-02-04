package com.nepxion.discovery.plugin.strategy.monitor;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedList;

public class StrategyTracerContext {

    private static final ThreadLocal<StrategyTracerContext> THREAD_LOCAL = ThreadLocal.withInitial(StrategyTracerContext::new);

    private LinkedList<Object> spanList = new LinkedList<>();

    public static StrategyTracerContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        StrategyTracerContext strategyTracerContext = THREAD_LOCAL.get();
        if (strategyTracerContext == null) {
            return;
        }

        LinkedList<Object> spanList = strategyTracerContext.getSpanList();
        if (!spanList.isEmpty()) {
            spanList.removeLast();
        }

        if (spanList.isEmpty()) {
            THREAD_LOCAL.remove();
        }
    }

    public Object getSpan() {
        if (spanList.isEmpty()) {
            return null;
        }

        return spanList.getLast();
    }

    public void setSpan(Object span) {
        spanList.addLast(span);
    }

    private LinkedList<Object> getSpanList() {
        return spanList;
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