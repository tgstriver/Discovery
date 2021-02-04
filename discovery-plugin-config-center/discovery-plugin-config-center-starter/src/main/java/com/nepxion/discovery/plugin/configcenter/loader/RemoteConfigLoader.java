package com.nepxion.discovery.plugin.configcenter.loader;

/**
 * 从远程配置中心加载配置信息
 */
public abstract class RemoteConfigLoader implements ConfigLoader {

    public abstract void subscribeConfig();

    public abstract void unsubscribeConfig();
}