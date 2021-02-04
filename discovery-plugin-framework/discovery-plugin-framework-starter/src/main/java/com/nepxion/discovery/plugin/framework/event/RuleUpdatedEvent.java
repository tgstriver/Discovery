package com.nepxion.discovery.plugin.framework.event;

import com.nepxion.discovery.common.entity.SubscriptionType;

import java.io.Serializable;

/**
 * 规则更新事件
 */
public class RuleUpdatedEvent implements Serializable {

    private static final long serialVersionUID = 2315578803987663866L;

    private SubscriptionType subscriptionType;
    private String rule;

    public RuleUpdatedEvent(SubscriptionType subscriptionType, String rule) {
        this.subscriptionType = subscriptionType;
        this.rule = rule;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public String getRule() {
        return rule;
    }
}