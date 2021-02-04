package com.nepxion.discovery.plugin.strategy.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.util.StrategyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public abstract class AbstractStrategyInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractStrategyInterceptor.class);

    @Autowired
    protected ConfigurableEnvironment environment;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_DEBUG_ENABLED + ":false}")
    protected Boolean interceptDebugEnabled;

    protected List<String> requestHeaderList = new ArrayList<>();

    public AbstractStrategyInterceptor(String contextRequestHeaders, String businessRequestHeaders) {
        if (StringUtils.isNotEmpty(contextRequestHeaders)) {
            requestHeaderList.addAll(StringUtil.splitToList(contextRequestHeaders.toLowerCase()));
        }

        if (StringUtils.isNotEmpty(businessRequestHeaders)) {
            requestHeaderList.addAll(StringUtil.splitToList(businessRequestHeaders.toLowerCase()));
        }

        LOG.info("------- " + getInterceptorName() + " Intercept Information -------");
        LOG.info(getInterceptorName() + " desires to intercept customer headers are {}", requestHeaderList);
        LOG.info("--------------------------------------------------");
    }

    /**
     * 拦截输入的请求头并输出
     */
    protected void interceptInputHeader() {
        if (!interceptDebugEnabled) {
            return;
        }

        Enumeration<String> headerNames = strategyContextHolder.getHeaderNames();
        if (headerNames != null) {
            System.out.println("------- " + getInterceptorName() + " Intercept Input Header Information -------");
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                boolean isHeaderContains = this.isHeaderContains(headerName.toLowerCase());
                if (isHeaderContains) {
                    String headerValue = strategyContextHolder.getHeader(headerName);
                    System.out.println(headerName + "=" + headerValue);
                }
            }
            System.out.println("--------------------------------------------------");
        }
    }

    protected boolean isHeaderContains(String headerName) {
        return headerName.startsWith(DiscoveryConstant.N_D_PREFIX) || requestHeaderList.contains(headerName);
    }

    protected boolean isHeaderContainsExcludeInner(String headerName) {
        return this.isHeaderContains(headerName) && !StrategyUtil.isInnerHeaderContains(headerName);
    }

    protected abstract String getInterceptorName();
}