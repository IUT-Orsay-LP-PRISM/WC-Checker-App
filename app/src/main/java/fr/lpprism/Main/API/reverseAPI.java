package fr.lpprism.Main.API;

public class reverseAPI {
    private Long place_id;
    private String licence;
    private String osm_type;
    private Long osm_id;
    private Double lat;
    private Double lon;
    private Integer place_rank;
    private String category;
    private String type;
    private Double importance;
    private String addresstype;
    private String name;
    private String display_name;
    private NominatimAddress address;
    private String[] boundingbox;

    class NominatimAddress {
        private String house_number;
        private String road;
        private String village;
        private String municipality;
        private String county;
        private String ISO3166_2_lvl6;
        private String state;
        private String ISO3166_2_lvl4;
        private String region;
        private String postcode;
        private String country;
        private String country_code;
    }

    public String getAdresse() {
        String adresse = "";
        if(address.house_number != null){
            adresse += address.house_number + ", ";
        }
        if(address.road != null){
            adresse += address.road + ", ";
        }
        if(address.postcode != null){
            adresse += address.postcode + " ";
        }
        if(address.village != null){
            adresse += address.village + ", ";
        }
        if(address.country != null){
            adresse += address.country;
        }
        return adresse;
    }
}
