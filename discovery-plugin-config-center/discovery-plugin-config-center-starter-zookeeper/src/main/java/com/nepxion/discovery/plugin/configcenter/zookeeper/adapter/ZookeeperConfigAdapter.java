package com.nepxion.discovery.plugin.configcenter.zookeeper.adapter;

import com.nepxion.discovery.common.zookeeper.constant.ZookeeperConstant;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperOperation;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.logger.ConfigLogger;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class ZookeeperConfigAdapter extends ConfigAdapter {

    @Autowired
    private ZookeeperOperation zookeeperOperation;

    @Autowired
    private ConfigLogger configLogger;

    private TreeCacheListener partialListener;
    private TreeCacheListener globalListener;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return zookeeperOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        partialListener = subscribeConfig(false);
        globalListener = subscribeConfig(true);
    }

    private TreeCacheListener subscribeConfig(boolean globalConfig) {
        String group = getGroup();
        String dataId = getDataId(globalConfig);

        configLogger.logSubscribeStarted(globalConfig);

        try {
            return zookeeperOperation.subscribeConfig(group, dataId, config -> callbackConfig(config, globalConfig));
        } catch (Exception e) {
            configLogger.logSubscribeFailed(e, globalConfig);
        }

        return null;
    }

    @Override
    public void unsubscribeConfig() {
        unsubscribeConfig(partialListener, false);
        unsubscribeConfig(globalListener, true);
    }

    private void unsubscribeConfig(TreeCacheListener configListener, boolean globalConfig) {
        if (configListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        configLogger.logUnsubscribeStarted(globalConfig);

        try {
            zookeeperOperation.unsubscribeConfig(group, dataId, configListener);
        } catch (Exception e) {
            configLogger.logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public String getConfigType() {
        return ZookeeperConstant.ZOOKEEPER_TYPE;
    }

    @Override
    public boolean isConfigSingleKey() {
        return false;
    }
}