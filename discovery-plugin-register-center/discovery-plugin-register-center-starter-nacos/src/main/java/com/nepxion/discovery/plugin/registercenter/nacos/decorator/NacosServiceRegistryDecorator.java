package com.nepxion.discovery.plugin.registercenter.nacos.decorator;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.listener.register.RegisterListenerExecutor;
import org.springframework.beans.BeansException;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 对基于nacos实现服务注册功能进行装饰
 */
public class NacosServiceRegistryDecorator extends NacosServiceRegistry {

    private NacosServiceRegistry serviceRegistry;
    private ConfigurableApplicationContext applicationContext;
    private ConfigurableEnvironment environment;

    public NacosServiceRegistryDecorator(NacosDiscoveryProperties nacosDiscoveryProperties, NacosServiceRegistry serviceRegistry, ConfigurableApplicationContext applicationContext) {
        super(nacosDiscoveryProperties);

        this.serviceRegistry = serviceRegistry;
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
    }

    @Override
    public void register(Registration registration) {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            try {
                RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
                registerListenerExecutor.onRegister(registration);
            } catch (BeansException e) {
                // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
            }
        }

        serviceRegistry.register(registration);
    }

    @Override
    public void deregister(Registration registration) {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            try {
                RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
                registerListenerExecutor.onDeregister(registration);
            } catch (BeansException e) {
                // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
            }
        }

        serviceRegistry.deregister(registration);
    }

    @Override
    public void setStatus(Registration registration, String status) {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            try {
                RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
                registerListenerExecutor.onSetStatus(registration, status);
            } catch (BeansException e) {
                // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
            }
        }

        serviceRegistry.setStatus(registration, status);
    }

    @Override
    public Object getStatus(Registration registration) {
        return serviceRegistry.getStatus(registration);
    }

    @Override
    public void close() {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            try {
                RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
                registerListenerExecutor.onClose();
            } catch (BeansException e) {
                // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
            }
        }

        serviceRegistry.close();
    }

    public ConfigurableEnvironment getEnvironment() {
        return environment;
    }
}