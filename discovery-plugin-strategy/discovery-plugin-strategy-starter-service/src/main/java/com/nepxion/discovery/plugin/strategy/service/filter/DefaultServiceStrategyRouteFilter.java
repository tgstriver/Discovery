package com.nepxion.discovery.plugin.strategy.service.filter;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

// 负载均衡前执行路由过滤
public class DefaultServiceStrategyRouteFilter extends AbstractServiceStrategyRouteFilter {

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Override
    public String getRouteVersion() {
        return strategyContextHolder.getRouteVersion();
    }

    @Override
    public String getRouteRegion() {
        return strategyContextHolder.getRouteRegion();
    }

    @Override
    public String getRouteEnvironment() {
        return strategyContextHolder.getRouteEnvironment();
    }

    @Override
    public String getRouteAddress() {
        return strategyContextHolder.getRouteAddress();
    }

    @Override
    public String getRouteVersionWeight() {
        return strategyContextHolder.getRouteVersionWeight();
    }

    @Override
    public String getRouteRegionWeight() {
        return strategyContextHolder.getRouteRegionWeight();
    }

    @Override
    public String getRouteIdBlacklist() {
        return strategyContextHolder.getRouteIdBlacklist();
    }

    @Override
    public String getRouteAddressBlacklist() {
        return strategyContextHolder.getRouteAddressBlacklist();
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }
}