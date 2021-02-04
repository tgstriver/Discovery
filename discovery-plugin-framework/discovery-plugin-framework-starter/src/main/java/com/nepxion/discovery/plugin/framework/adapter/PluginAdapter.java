package com.nepxion.discovery.plugin.framework.adapter;

import com.nepxion.discovery.common.entity.RuleEntity;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Map;

/**
 * 插件适配器
 */
public interface PluginAdapter {

    /********************************************************************************************
     *
     ************************************* 服务消费者信息 ******************************************
     *
     *********************************************************************************************
     */

    String getPlugin();

    String getGroupKey();

    String getGroup();

    String getServiceType();

    String getServiceId();

    String getServiceAppId();

    String getServiceUUId();

    String getHost();

    int getPort();

    Map<String, String> getMetadata();

    String getVersion();

    String getLocalVersion();

    String getDynamicVersion();

    void setDynamicVersion(String version);

    void clearDynamicVersion();

    RuleEntity getRule();

    RuleEntity getLocalRule();

    void setLocalRule(RuleEntity ruleEntity);

    RuleEntity getDynamicRule();

    RuleEntity getDynamicPartialRule();

    void setDynamicPartialRule(RuleEntity ruleEntity);

    void clearDynamicPartialRule();

    RuleEntity getDynamicGlobalRule();

    void setDynamicGlobalRule(RuleEntity ruleEntity);

    void clearDynamicGlobalRule();

    String getRegion();

    String getEnvironment();

    String getZone();

    String getContextPath();

    /********************************************************************************************
     *
     ************************************* 服务提供者信息 ******************************************
     *
     *********************************************************************************************
     */

    Map<String, String> getServerMetadata(Server server);

    String getServerPlugin(Server server);

    String getServerGroupKey(Server server);

    String getServerGroup(Server server);

    String getServerServiceType(Server server);

    String getServerServiceId(Server server);

    String getServerServiceUUId(Server server);

    String getServerVersion(Server server);

    String getServerRegion(Server server);

    String getServerEnvironment(Server server);

    String getServerZone(Server server);

    String getServerContextPath(Server server);

    /********************************************************************************************
     *
     ************************************* 服务实例信息 ******************************************
     *
     *********************************************************************************************
     */

    Map<String, String> getInstanceMetadata(ServiceInstance instance);

    String getInstancePlugin(ServiceInstance instance);

    String getInstanceGroupKey(ServiceInstance instance);

    String getInstanceGroup(ServiceInstance instance);

    String getInstanceServiceType(ServiceInstance instance);

    String getInstanceServiceId(ServiceInstance instance);

    String getInstanceServiceUUId(ServiceInstance instance);

    String getInstanceVersion(ServiceInstance instance);

    String getInstanceRegion(ServiceInstance instance);

    String getInstanceEnvironment(ServiceInstance instance);

    String getInstanceZone(ServiceInstance instance);

    String getInstanceContextPath(ServiceInstance instance);

    String getPluginInfo(String previousPluginInfo);
}