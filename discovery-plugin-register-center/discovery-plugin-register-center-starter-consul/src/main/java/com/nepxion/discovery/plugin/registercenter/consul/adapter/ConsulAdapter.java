package com.nepxion.discovery.plugin.registercenter.consul.adapter;

import com.nepxion.discovery.plugin.framework.adapter.AbstractPluginAdapter;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.consul.discovery.ConsulServer;

import java.util.Map;

public class ConsulAdapter extends AbstractPluginAdapter {

    @Override
    public Map<String, String> getServerMetadata(Server server) {
        if (server instanceof ConsulServer) {
            ConsulServer consulServer = (ConsulServer) server;
            return consulServer.getMetadata();
        }

        return emptyMetadata;

    }
}