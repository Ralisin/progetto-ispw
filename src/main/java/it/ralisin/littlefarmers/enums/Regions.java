package it.ralisin.littlefarmers.enums;

import java.util.Objects;

public enum Regions {
    ABRUZZO("Abruzzo"),
    BASILICATA("Basilicata"),
    CALABRIA("Calabria"),
    CAMPANIA("Campania"),
    EMILIAROMAGNA("Emilia Romagna"),
    FRIULIVENEZIAGIULIA("Friuli Venezia Giulia"),
    LAZIO("Lazio"),
    LIGURIA("Liguria"),
    LOMBARDIA("Lombardia"),
    MARCHE("Marche"),
    MOLISE("Molise"),
    PIEMONTE("Piemonte"),
    PUGLIA("Puglia"),
    SARDEGNA("Sardegna"),
    SICILIA("Sicilia"),
    TOSCANA("Toscana"),
    TRENTINOALTOADIGE("Trentino Alto Adige"),
    UMBRIA("Umbria"),
    VALDAOSTA("Val d'Aosta"),
    VENETO("Veneto");

    private final String region;

    Regions(String region) {
        this.region = region;
    }

    public String getRegionString() {
        return region;
    }

    public static Regions getByRegion(String region) {
        for(Regions r : Regions.values()) {
            if(Objects.equals(r.getRegionString(), region)) return r;
        }

        throw new IllegalArgumentException("No enum constant with ID: " + region);
    }
}
