package com.nepxion.discovery.plugin.strategy.context;

import java.util.Enumeration;

public interface StrategyContextHolder {

    Enumeration<String> getHeaderNames();

    String getHeader(String name);

    String getParameter(String name);

    String getCookie(String name);

    String getRouteVersion();

    String getRouteRegion();

    String getRouteEnvironment();

    String getRouteAddress();

    String getRouteVersionWeight();

    String getRouteRegionWeight();

    String getRouteIdBlacklist();

    String getRouteAddressBlacklist();
}