package com.nepxion.discovery.plugin.strategy.filter;

public interface StrategyRouteFilter {

    String getRouteVersion();

    String getRouteRegion();

    String getRouteEnvironment();

    String getRouteAddress();

    String getRouteVersionWeight();

    String getRouteRegionWeight();

    String getRouteIdBlacklist();

    String getRouteAddressBlacklist();
}