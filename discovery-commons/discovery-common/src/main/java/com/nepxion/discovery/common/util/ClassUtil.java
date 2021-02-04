package com.nepxion.discovery.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClassUtil {

    /**
     * 将参数名和参数值进行映射到map中，key为参数名，value为参数值
     *
     * @param methodParameterNames
     * @param arguments
     * @return
     */
    public static Map<String, Object> getParameterMap(String[] methodParameterNames, Object[] arguments) {
        Map<String, Object> parameterMap = new LinkedHashMap<>();
        if (ArrayUtils.isNotEmpty(arguments)) {
            for (int i = 0; i < arguments.length; i++) {
                String parameterName;
                if (ArrayUtils.isNotEmpty(methodParameterNames)) {
                    parameterName = methodParameterNames[i];
                } else {
                    parameterName = String.valueOf(i);
                }

                Object argument = arguments[i];
                parameterMap.put(parameterName, argument);
            }
        }

        return parameterMap;
    }
}