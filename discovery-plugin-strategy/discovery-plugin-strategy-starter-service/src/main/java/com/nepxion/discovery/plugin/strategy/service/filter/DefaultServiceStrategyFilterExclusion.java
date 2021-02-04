package com.nepxion.discovery.plugin.strategy.service.filter;

import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DefaultServiceStrategyFilterExclusion implements ServiceStrategyFilterExclusion {

    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_URI_FILTER_EXCLUSION + ":/actuator/}")
    protected String uriFilterExclusion;

    protected List<String> uriFilterExclusionList = new ArrayList<>();

    @PostConstruct
    private void initialize() {
        if (StringUtils.isNotEmpty(uriFilterExclusion)) {
            uriFilterExclusionList = StringUtil.splitToList(uriFilterExclusion);
        }
    }

    @Override
    public boolean isExclusion(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();

        for (String uriFilterExclusionValue : uriFilterExclusionList) {
            if (requestURI.contains(uriFilterExclusionValue)) {
                return true;
            }
        }

        return false;
    }
}