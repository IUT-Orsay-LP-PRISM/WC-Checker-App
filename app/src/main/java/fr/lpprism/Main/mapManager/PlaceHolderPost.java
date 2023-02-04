package fr.lpprism.Main.mapManager;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class PlaceHolderPost {
    private String id;
    private String datasetid;
    private String coordinatesX;
    private String coordinatesY;
    private String adresse;
    private String type;
    private String horaires;
    private String acces_pmr;
    private String relais_bebe;
    private String free;

    public String getLongitude() {
        return this.coordinatesX;
    }
    public String getLatitude() {
        return this.coordinatesY;
    }

    public String getAdresse(){
        return this.adresse;
    }

    public String getType() {
        return this.type;
    }


}