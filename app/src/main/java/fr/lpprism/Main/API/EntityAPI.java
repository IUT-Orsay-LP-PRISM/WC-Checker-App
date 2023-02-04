package fr.lpprism.Main.API;

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


}