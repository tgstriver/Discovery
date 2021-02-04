package com.nepxion.discovery.common.nacos.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @version 1.0
 */

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.nepxion.discovery.common.nacos.constant.NacosConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.concurrent.Executor;

public class NacosOperation {

    @Autowired
    private ConfigService nacosConfigService;
    @Autowired
    private Environment environment;

    public String getConfig(String group, String serviceId) throws NacosException {
        long timeout = environment.getProperty(NacosConstant.NACOS_PLUGIN_TIMEOUT, Long.class, NacosConstant.NACOS_PLUGIN_DEFAULT_TIMEOUT);

        return nacosConfigService.getConfig(serviceId, group, timeout);
    }

    public boolean removeConfig(String group, String serviceId) throws NacosException {
        return nacosConfigService.removeConfig(serviceId, group);
    }

    public boolean publishConfig(String group, String serviceId, String config) throws NacosException {
        return nacosConfigService.publishConfig(serviceId, group, config);
    }

    public Listener subscribeConfig(String group, String serviceId, Executor executor, NacosSubscribeCallback nacosSubscribeCallback) throws NacosException {
        Listener configListener = new Listener() {
            @Override
            public void receiveConfigInfo(String config) {
                nacosSubscribeCallback.callback(config);
            }

            @Override
            public Executor getExecutor() {
                return executor;
            }
        };

        nacosConfigService.addListener(serviceId, group, configListener);

        return configListener;
    }

    public void unsubscribeConfig(String group, String serviceId, Listener configListener) {
        nacosConfigService.removeListener(serviceId, group, configListener);
    }
}