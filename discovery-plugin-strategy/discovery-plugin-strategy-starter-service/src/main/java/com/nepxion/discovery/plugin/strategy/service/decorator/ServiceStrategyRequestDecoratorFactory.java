package com.nepxion.discovery.plugin.strategy.service.decorator;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 工厂方法模式
 */
public class ServiceStrategyRequestDecoratorFactory {

    public static RequestAttributes decorateRequestAttributes(RequestAttributes requestAttributes) {
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return new ServletRequestAttributes(new ServiceStrategyRequestDecorator(request));
    }
}