package com.nepxion.discovery.plugin.strategy.skywalking.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracer;
import com.nepxion.discovery.plugin.strategy.skywalking.constant.SkyWalkingStrategyConstant;
import com.nepxion.discovery.plugin.strategy.skywalking.monitor.SkyWalkingStrategyTracer;
import com.taobao.text.Color;

@Configuration
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class SkyWalkingStrategyAutoConfiguration {

    static {
        LogoBanner logoBanner = new LogoBanner(SkyWalkingStrategyAutoConfiguration.class, "/com/nepxion/skywalking/resource/logo.txt", "Welcome to Nepxion", 7, 5, new Color[]{Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta, Color.red}, true);
        NepxionBanner.show(logoBanner, new Description("Tracing:", SkyWalkingStrategyConstant.SKYWALKING_TYPE, 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED)
    public StrategyTracer strategyTracer() {
        return new SkyWalkingStrategyTracer();
    }
}