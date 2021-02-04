package com.nepxion.discovery.common.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VersionFilterEntity implements Serializable {
    private static final long serialVersionUID = -6147106004826964165L;

    private Map<String, List<VersionEntity>> versionEntityMap = new LinkedHashMap<>();

    public Map<String, List<VersionEntity>> getVersionEntityMap() {
        return versionEntityMap;
    }

    public void setVersionEntityMap(Map<String, List<VersionEntity>> versionEntityMap) {
        this.versionEntityMap = versionEntityMap;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}