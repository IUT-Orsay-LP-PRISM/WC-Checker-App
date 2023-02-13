package fr.lpprism.Main.API;

import java.util.ArrayList;

public class EntityAPI {
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
    public ArrayList<Boolean> getSwitch() {
        ArrayList<Boolean> switchList = new ArrayList<Boolean>();
        switchList.add(this.acces_pmr.equals("1"));
        switchList.add(this.free.equals("1"));
        switchList.add(this.relais_bebe.equals("1"));
        return switchList;
    }
}