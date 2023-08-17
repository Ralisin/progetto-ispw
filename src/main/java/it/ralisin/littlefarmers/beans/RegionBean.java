package it.ralisin.littlefarmers.beans;

import it.ralisin.littlefarmers.enums.Regions;

public class RegionBean {
    private final Regions region;

    public RegionBean(String region) {
        this.region = Regions.getByRegionString(region);
    }

    public Regions getRegions() {
        return region;
    }
}
