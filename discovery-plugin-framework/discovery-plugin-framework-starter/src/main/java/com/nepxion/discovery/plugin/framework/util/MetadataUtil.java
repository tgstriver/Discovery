package com.nepxion.discovery.plugin.framework.util;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MetadataUtil {

    /**
     * 如果通过系统属性设置了'metadata.'开头的值，那么就用系统属性覆盖应用程序中设置的值
     *
     * @param metadata
     */
    public static void filter(Map<String, String> metadata) {
        Properties properties = System.getProperties();
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            if (propertyName.startsWith(DiscoveryConstant.METADATA + ".")) {
                String key = propertyName.substring((DiscoveryConstant.METADATA + ".").length());
                String value = properties.get(propertyName).toString();
                metadata.put(key, value);
            }
        }
    }

    public static void filter(List<String> metadata) {
        Properties properties = System.getProperties();
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            if (propertyName.startsWith(DiscoveryConstant.METADATA + ".")) {
                String key = propertyName.substring((DiscoveryConstant.METADATA + ".").length());
                String value = properties.get(propertyName).toString();

                int index = getIndex(metadata, key);
                if (index > -1) {
                    metadata.set(index, key + "=" + value);
                } else {
                    metadata.add(key + "=" + value);
                }
            }
        }
    }

    public static int getIndex(List<String> metadata, String key) {
        for (int i = 0; i < metadata.size(); i++) {
            String value = metadata.get(i);
            if (value.startsWith(key + "=")) {
                return i;
            }
        }

        return -1;
    }

    public static boolean containsKey(List<String> metadata, String key) {
        for (String value : metadata) {
            if (value.startsWith(key + "=")) {
                return true;
            }
        }

        return false;
    }
}