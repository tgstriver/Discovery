package com.nepxion.discovery.plugin.configcenter.apollo.adapter;

import com.ctrip.framework.foundation.Foundation;
import com.nepxion.discovery.plugin.framework.adapter.ApplicationInfoAdapter;

public class ApolloApplicationInfoAdapter implements ApplicationInfoAdapter {

    @Override
    public String getAppId() {
        return Foundation.app().getAppId();
    }
}