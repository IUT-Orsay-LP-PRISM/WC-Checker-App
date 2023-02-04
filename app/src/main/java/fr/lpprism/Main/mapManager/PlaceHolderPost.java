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
    public String getSwitch() {
        String switchString = "";
        if (this.acces_pmr.equals("1")) {
            switchString += "Accès Mobilité Réduite\n";
        }
        if (this.relais_bebe.equals("1")) {
            switchString += "Relais bébé\n";
        }
        if (this.free.equals("1")) {
            switchString += "Gratuit\n";
        }
        return switchString;
    }
}