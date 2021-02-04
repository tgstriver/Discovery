package com.nepxion.discovery.plugin.registercenter.nacos.adapter;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.nepxion.discovery.plugin.framework.adapter.AbstractPluginAdapter;
import com.netflix.loadbalancer.Server;

import java.util.Map;

public class NacosAdapter extends AbstractPluginAdapter {

    @Override
    public Map<String, String> getServerMetadata(Server server) {
        if (server instanceof NacosServer) {
            NacosServer nacosServer = (NacosServer) server;
            return nacosServer.getMetadata();
        }

        return super.emptyMetadata;

    }
}