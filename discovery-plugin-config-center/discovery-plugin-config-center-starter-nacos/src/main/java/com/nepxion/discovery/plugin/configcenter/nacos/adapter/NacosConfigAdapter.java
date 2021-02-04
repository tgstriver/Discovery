package com.nepxion.discovery.plugin.configcenter.nacos.adapter;

import com.alibaba.nacos.api.config.listener.Listener;
import com.nepxion.discovery.common.nacos.constant.NacosConstant;
import com.nepxion.discovery.common.nacos.operation.NacosOperation;
import com.nepxion.discovery.common.thread.DiscoveryNamedThreadFactory;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.logger.ConfigLogger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NacosConfigAdapter extends ConfigAdapter {

    private ExecutorService executorService = new ThreadPoolExecutor(2, 4,
            0, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(1),
            new DiscoveryNamedThreadFactory("nacos-config"),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    @Autowired
    private NacosOperation nacosOperation;

    @Autowired
    private ConfigLogger configLogger;

    private Listener partialListener;
    private Listener globalListener;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return nacosOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        partialListener = this.subscribeConfig(false);
        globalListener = this.subscribeConfig(true);
    }

    private Listener subscribeConfig(boolean globalConfig) {
        String group = super.getGroup();
        String dataId = super.getDataId(globalConfig);

        configLogger.logSubscribeStarted(globalConfig);

        try {
            return nacosOperation.subscribeConfig(group, dataId, executorService, config -> super.callbackConfig(config, globalConfig));
        } catch (Exception e) {
            configLogger.logSubscribeFailed(e, globalConfig);
        }

        return null;
    }

    @Override
    public void unsubscribeConfig() {
        this.unsubscribeConfig(partialListener, false);
        this.unsubscribeConfig(globalListener, true);

        executorService.shutdownNow();
    }

    private void unsubscribeConfig(Listener configListener, boolean globalConfig) {
        if (configListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        configLogger.logUnsubscribeStarted(globalConfig);

        try {
            nacosOperation.unsubscribeConfig(group, dataId, configListener);
        } catch (Exception e) {
            configLogger.logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public String getConfigType() {
        return NacosConstant.NACOS_TYPE;
    }

    @Override
    public boolean isConfigSingleKey() {
        return false;
    }
}