package com.nepxion.discovery.plugin.registercenter.eureka.adapter;

import com.nepxion.discovery.plugin.framework.adapter.AbstractPluginAdapter;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import java.util.Map;

public class EurekaAdapter extends AbstractPluginAdapter {

    @Override
    public Map<String, String> getServerMetadata(Server server) {
        if (server instanceof DiscoveryEnabledServer) {
            DiscoveryEnabledServer discoveryEnabledServer = (DiscoveryEnabledServer) server;
            return discoveryEnabledServer.getInstanceInfo().getMetadata();
        }

        return emptyMetadata;

    }
}