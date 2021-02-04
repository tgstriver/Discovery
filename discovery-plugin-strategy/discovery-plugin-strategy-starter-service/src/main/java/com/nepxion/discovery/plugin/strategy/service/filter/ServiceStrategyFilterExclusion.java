package com.nepxion.discovery.plugin.strategy.service.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServiceStrategyFilterExclusion {

    boolean isExclusion(HttpServletRequest request, HttpServletResponse response);
}