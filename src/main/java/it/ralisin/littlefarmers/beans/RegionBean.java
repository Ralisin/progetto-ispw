package it.ralisin.littlefarmers.beans;

import it.ralisin.littlefarmers.enums.Regions;

public class RegionBean {
    private final Regions region;

    public RegionBean(Regions region) {
        this.region = region;
    }

    public Regions getRegion() {
        return region;
    }
}
