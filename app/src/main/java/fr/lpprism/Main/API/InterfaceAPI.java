package fr.lpprism.Main.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceAPI {
    @GET("getAllToilettes")
    Call<EntityAPI[]> getAllToilettes();

    @POST("addToilette/{coordinatesX}/{coordinatesY}/{adresse}/{type}/{horaires}/{acces_pmr}/{relais_bebe}/{free}")
    Call<EntityAPI> addToilette(@Path("coordinatesX") float coordinatesX, @Path("coordinatesY") float coordinatesY, @Path("adresse") String adresse, @Path("type") String type, @Path("horaires") String horaires, @Path("acces_pmr") boolean acces_pmr, @Path("relais_bebe") boolean relais_bebe, @Path("free") boolean free);

}

