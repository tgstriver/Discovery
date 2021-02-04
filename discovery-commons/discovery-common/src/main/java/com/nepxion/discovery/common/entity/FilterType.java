package com.nepxion.discovery.common.entity;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

/**
 * 过滤器类型
 */
public enum FilterType {

    /**
     * 黑名单
     */
    BLACKLIST(DiscoveryConstant.BLACKLIST),

    /**
     * 白名单
     */
    WHITELIST(DiscoveryConstant.WHITELIST);

    private String value;

    private FilterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FilterType fromString(String value) {
        for (FilterType type : FilterType.values()) {
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