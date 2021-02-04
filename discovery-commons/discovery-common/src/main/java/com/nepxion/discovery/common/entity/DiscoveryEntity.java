package com.nepxion.discovery.common.entity;

/**
 * 服务发现entity
 */
public class DiscoveryEntity extends FilterHolderEntity {

    private static final long serialVersionUID = -7417362859952278987L;

    private VersionFilterEntity versionFilterEntity;
    private RegionFilterEntity regionFilterEntity;
    private WeightFilterEntity weightFilterEntity;

    public VersionFilterEntity getVersionFilterEntity() {
        return versionFilterEntity;
    }

    public void setVersionFilterEntity(VersionFilterEntity versionFilterEntity) {
        this.versionFilterEntity = versionFilterEntity;
    }

    public RegionFilterEntity getRegionFilterEntity() {
        return regionFilterEntity;
    }

    public void setRegionFilterEntity(RegionFilterEntity regionFilterEntity) {
        this.regionFilterEntity = regionFilterEntity;
    }

    public WeightFilterEntity getWeightFilterEntity() {
        return weightFilterEntity;
    }

    public void setWeightFilterEntity(WeightFilterEntity weightFilterEntity) {
        this.weightFilterEntity = weightFilterEntity;
    }
}