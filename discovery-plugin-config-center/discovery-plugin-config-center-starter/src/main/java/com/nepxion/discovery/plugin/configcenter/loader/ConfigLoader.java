package com.nepxion.discovery.plugin.configcenter.loader;

/**
 * 配置加载器
 */
public interface ConfigLoader {

    /**
     * 获取配置列表
     *
     * @return
     * @throws Exception
     */
    String[] getConfigList() throws Exception;
}