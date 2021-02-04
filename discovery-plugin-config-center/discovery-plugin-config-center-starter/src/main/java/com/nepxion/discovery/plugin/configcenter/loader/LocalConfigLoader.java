package com.nepxion.discovery.plugin.configcenter.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.plugin.framework.util.FileContextUtil;

/**
 * 从本地配置文件中加载配置信息
 */
public abstract class LocalConfigLoader implements ConfigLoader {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String[] getConfigList() throws Exception {
        String path = this.getPath();

        String[] config = new String[1];
        config[0] = FileContextUtil.getText(applicationContext, path);
        return config;
    }

    protected abstract String getPath();
}