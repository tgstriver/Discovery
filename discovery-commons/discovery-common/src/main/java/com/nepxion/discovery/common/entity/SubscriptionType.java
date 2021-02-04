package com.nepxion.discovery.common.entity;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

/**
 * 配置信息订阅类型
 */
public enum SubscriptionType {

    /**
     * 部分订阅，到dataId上
     */
    PARTIAL(DiscoveryConstant.PARTIAL),

    /**
     * 全局订阅，到group上
     */
    GLOBAL(DiscoveryConstant.GLOBAL);

    private String value;

    SubscriptionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SubscriptionType fromString(String value) {
        for (SubscriptionType type : SubscriptionType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("No matched type with value=" + value);
    }

    @Override
    public String toString() {
        return value;
    }
}